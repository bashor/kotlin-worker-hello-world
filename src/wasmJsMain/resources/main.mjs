// import * as foo from "./kotlin-worker-hello-world.mjs" // TODO issue? no import.meta.resolve
//
// export default { ...foo };
//
import { importObject } from "./kotlin-worker-hello-world.import-object.mjs"
import source wasmModule from "./kotlin-worker-hello-world.wasm"

const wasmOptions = { builtins: ['js-string'], importedStringConstants: "'" }
const wasmInstance = new WebAssembly.Instance(wasmModule, importObject, wasmOptions);

export default { ... wasmInstance.exports };
// export default wasmInstance.exports; // TODO issue? Exported value's prototype chain does not end in Object.
