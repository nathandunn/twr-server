//grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.7
grails.project.source.level = 1.7
//grails.project.war.file = "target/${appName}-${appVersion}.war"

// uncomment (and adjust settings) to fork the JVM to isolate classpaths
//grails.project.fork = [
//   run: [maxMemory:1024, minMemory:64, debug:false, maxPerm:256]
//]

grails.project.fork = [
        // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
        //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

        // configure settings for the test-app JVM, uses the daemon by default
        test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
        // configure settings for the run-app JVM
        run: [maxMemory: 2048, minMemory: 64, debug: false, maxPerm: 1024, forkReserve:false],
        // configure settings for the run-war JVM
        war: [maxMemory: 2048, minMemory: 64, debug: false, maxPerm: 1024, forkReserve:false],
//        war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
        // configure settings for the Console UI JVM
        console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]


grails.project.dependency.resolver = "maven" // or ivy

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
//    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    log "verbose" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenLocal()
        mavenCentral()

        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.

        runtime 'mysql:mysql-connector-java:5.1.30'
//        runtime 'mysql:mysql-connector-java:5.1.16'
        runtime 'org.jsoup:jsoup:1.7.2'
    }

    plugins {

        build ':tomcat:7.0.42'

        compile ':cache:1.1.1'
//        compile ":quartz:1.0.1"
        compile ":executor:0.3"
//        compile ":rest-client-builder:1.0.3"
        compile ":rest-client-builder:2.0.0"
        compile ":scaffolding:2.0.3"
//        compile ":richui:0.8"

//        compile ":ckeditor:3.6.6.1.0"
        compile ":ckeditor:3.6.6.1.1"


        // plugins needed at runtime but not for compilation
        runtime ":jquery:1.10.2"
        runtime ":resources:1.2.1"
        runtime ':hibernate:3.6.10.2'

        compile ":mail:1.0.1"
        // plugins.shiro=1.1.4
//        compile ":shiro:1.2.0"
        compile (":shiro:latest.release") {
            excludes "servlet-api"
            excludes "quartz"
        }
        compile ':quartz:1.0.1'

        compile ":crypto:2.0"

        // Uncomment these (or add new ones) to enable additional resources capabilities
        //runtime ":zipped-resources:1.0"
        //runtime ":cached-resources:1.0"
        //runtime ":yui-minify-resources:0.1.5"
        compile ":platform-core:1.0.RC5"

//        build ":tomcat:$grailsVersion"

//        runtime ":database-migration:1.3.2"
        runtime ":database-migration:1.3.8"


    }
}
