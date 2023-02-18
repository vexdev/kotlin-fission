# Kotlin Environment

⚠️ **DERIVED WORK** ⚠️

This software is derived from the kotlin runtime, which unfortunately uses alpine which is not compatible with kotlin native.

The `kotlin` runtime is a go server that uses a subprocess to invoke kotlin-native executables.

When executing functions using binaries, **ensure that the executable is built for the right architecture**.
Using the default kotlin environment this means that the binary should be build for Linux.

## Usage

To get started with the latest kotlin environment:

```bash
fission env create --name kotlin --image fission/kotlin-env --builder fission/kotlin-builder
```

The interface to the executable used by this environment is somewhat similar to a [CGI interface](https://en.wikipedia.org/wiki/Common_Gateway_Interface).
This means that any HTTP headers are converted to environment variables of the form "HTTP_<header-name>".
For example these are some of frequently occurring headers:

```bash
# Request Metadata
CONTENT_LENGTH
REQUEST_URI
REQUEST_METHOD

# HTTP Headers
HTTP_ACCEPT
HTTP_USER-AGENT
HTTP_CONTENT-TYPE
# ...
```

The body of HTTP piped over the STDIN to the executable.
All output that is provided to the server over the STDOUT will be transformed into the HTTP response.

## Compiling

To build the runtime environment:

```bash
docker build --tag=${USER}/kotlin-env .
```