# Kotlin Fission

This small wrapper allows you to easilly run kotlin native functions in fission.io

## Usage

### Writing your function

Following the example of the [simple echo function](https://github.com/vexdev/kotlin-fission-echo-example),

First add the dependency (`com.vexdev:kotlin-fission`) to your kotlin-native project:

```kotlin
repositories {
    mavenCentral() // <- Important!
}

kotlin {
    sourceSets {
        val nativeMain by getting {
            dependencies {
                implementation("com.vexdev:kotlin-fission:1.0.0")
            }
        }
    }
}
```

And then register your function in the main:

```kotlin
import fission.FissionFunction
import fission.Function
import fission.Header
import fission.Request

fun main() {
    FissionFunction.init(EchoFunction())
}

class EchoFunction : Function {
    override fun onCall(request: Request, headers: List<Header>): String {
        return "Content-Length: ${request.contentLength}\n" +
                "URI: ${request.uri}\n" +
                "Method: ${request.method}\n" +
                "--- BODY BEGINS ---\n" +
                "${request.content}\n" +
                "--- BODY ENDS ---\n"
    }
}
```

### Deployment

Kotlin-fission requires a specialised environment in fission.io.

This environment is available on docker-hub and can be used as follows:

```shell
# First, create the environment
$ fission environment create --name kotlin --image vexdev/fission-kotlin-env --keeparchive --version 3

# Then, create your function
$ fission function create --name echo --env kotlin --code build/bin/native/releaseExecutable/EchoFunction.kexe

# And now, you can create routes
$ fission route create --url /echo --function echo
$ curl http://localhost:$PORT/echo

# Or you can test it directly with the CLI
$ fission function test --name echo -v2
```