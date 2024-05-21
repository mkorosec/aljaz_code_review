1. request counter
kaj ce imas 3 instance gateway service-a, load balancer bo skrbel da vsaka instanca dobi nekaj requestov
kako bo gateway stel requeste, vsak pod posebej, ali imas kak skupni stevec? ni narobe ne eno ne drugo, samo vprasanje kaj so hotli in da si ready na odgovor. podvprasanje: karkoli ti odgovoris, zaj pa naredi obratno :) torej ce reces da vsak pod za sebe steje, kak bi naredo da mas globalni counter. in obratno.

2. navodila za build 
zacel sem s tem da bi rad samo zagnal vse, nea poznam nic o tem kak si strukturiral in sem samo sledil navodilom - nea dela brez da malo razmislis. pricakoval bi da lahko na root folderju nekak sprozim ta build, tak je tud napisano. v bistvu pa mors v vsak modul not it pa buildat.
to bi jaz malo bolj jasno napisal, pa mogoce kr vse commande napisal za rocni build "cd actors && mvn ..."

3. makefile za avtomatizacijo navodil in build procesa
se boljse kot navodila, avtomatiziraj. poglej si Makefile
novo navodilo
- za build: "make build"
- za start: "make start" al pa "make run"

sem ti napisal makefile s chatgpt-jev, v root folderju pozeni "make help"

4. keycloak setup
nea se mi da rocno setupa delat, to bi jaz tud avtomatiziral. verjetno lahko nek API request postnes na keycloak da te role ustvari. "make keycloak-setup"
ce misli to nekdo dejansko poganjat bi blo fajn, meni se ze nea da jebat s tem :D

5. meni je build actors failal
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.11.0:compile (default-compile) on project actors: Fatal error compiling: invalid flag: --release 

verjetno bi rabo novejso javo

mvn --version
Apache Maven 3.9.6 (bc0240f3c744dd6b6ec2920b3cd08dcc295161ae)
Maven home: /home/mkorosec/maven
Java version: 1.8.0_402, vendor: Amazon.com Inc., runtime: /opt/java/amazon-corretto-8.402.08.1-linux-x64/jre
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "6.5.0-35-generic", arch: "amd64", family: "unix"

6. devops / ci
nevem ce so to kaj omenjali, automated build pa testing pa take stvari, to ti github zgleda na izi omogoca, samo mogoce je out of scope
vidim da mas teste pa to vse napisano, tu se gre samo za zaganjanje tega na izi nacin
https://github.blog/2022-02-02-build-ci-cd-pipeline-github-actions-four-steps/

7. actors
actors service sem si malo boljse pogledal, kaj bi jaz vprasal:

7.1. zakaj mas Actor.java in ActorDto.java kot locena classa. ni nujno da je naredo, samo bi blo fajn da mas smiselno razlago

7.2. to je v actors service, nea poznam tocno sintakse. to je zdaj client al controller al oboje? 
- tale path mi zgleda kot da je v bistvu controller na actors service-u: /api/v1/actors/{actorId}/movies
- pol pa uporabis class ActorMovieClient v helperju da to dejansko klices

zaj ce prav berem kaj ta feignClient dela, pol je prvi stavek narobe in to nea expose-a nic na strani actor, to je dejansko samo client ki bo klical actor-movie/api/v1/actors/{actorId}/movies

cist ok, samo pisem sproti ko mi je kaj cudno. mogoce bo mel review-er iste zaznamke pa te bo vprasal kaj takega.

@FeignClient(name = "actor-movie")
public interface ActorMovieClient {

    @GetMapping("/api/v1/actors/{actorId}/movies")
    ResponseEntity<Collection<MovieDto>> getActorMovies(@NotNull @PathVariable Long actorId);

    @DeleteMapping("/api/v1/actors/{actorId}")
    ResponseEntity<Void> deleteActorMovieByActor(@NotNull @PathVariable Long actorId);



8. health checks

8.1. keycloak nima health checka

8.2. kaj se zgodi ce scale-as actors?

localhost:8080 -> to je naslov load balancerja za actors? al je to naslov do ene instance actors service-a? kaj se zgodi ce startas dva container-a? bi sprobal ce load balancing dela prek tega porta al ne
- load balancing dela -> pol ta health check ni vrei, moral bi testirat vsak pod posebej, drugace en pod crkne, health check malo dela, malo ne
- load balancing ne dela -> pol je health check ok, samo load balancing nimas :D

  actors:
    image: "actors:0.0.1-SNAPSHOT"
    container_name: actors-ms
    healthcheck:
      test: "curl --fail --silent localhost:8080/actuator/health/readiness | grep UP || exit 1"

9. load balancing

vedno bolj sumim da load balancing nea dela :)
- see #8.2, kjer sem prvic posumil
- baza: spring.datasource.url=jdbc:h2:mem:movie_db. sem vprasal chatgpt pa je to in-memory database. torej dejansko skalirat pod-ov nea mors, ker pol mas 3 instance actors service-a, kjer ma vsak svojo bazo in svoje podatke.

zaj mogoce ni hard requirement pa je cist ok, jaz sem iz tvojega README razumel kot da bi to moglo delat

na tej tocki bi moje vprasanje blo - kaj bi se moral narediti da bi lahko imel 2 replici vsakega poda
- shared db, deployed separately
- 8080 port dejansko kaze na load balancer ki potem dalje forwarda na posamicni pod
- health check mors met pol drugace
- pa verjetno se kaj