# Kotlin hello world for Cloudflare Workers

Your Kotlin code in [main.kt](https://github.com/cloudflare/kotlin-worker-hello-world/blob/master/src/main/kotlin/main.kt), running on Cloudflare Workers

In addition to [Wrangler v2.x](https://github.com/cloudflare/wrangler2) you will need to install Kotlin, including a JDK and support for Gradle projects. The easiest way to do this is using the free Community Edition of [IntelliJ IDEA](https://kotlinlang.org/docs/tutorials/jvm-get-started.html).

## Wrangler

Configure the [wrangler.toml](wrangler.toml) by filling in the `account_id` from the Workers pages of your Cloudflare Dashboard.

Further documentation for Wrangler can be found [here](https://developers.cloudflare.com/workers/tooling/wrangler).

## Gradle

After setting up Kotlin per the linked instructions above,

```
./gradlew :compileProductionExecutableKotlinWasmJs
```

That will compile your code and package it into a WebAssembly executable and JavaScript glue code, 
after which you can run `wrangler deploy` to push it to Cloudflare.

```
npx wrangler@latest deploy build/js/packages/kotlin-worker-hello-world-wasm-js/kotlin/kotlin-worker-hello-world-wasm-js.mjs
```

For more information on interop between Kotlin and JavaScript, see the [Kotlin docs](https://kotlinlang.org/docs/wasm-js-interop.html).