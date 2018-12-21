# Interpretação e Compilação de Linguagens - 2018

**Trabalho prático realizado por:**

* João Marques n.° 48500
* Vicente Almeida n.° 47803

O trabalho prático implementa todas as funcionalidades pedidas no enunciado
fornecido. O compilador para a linguagem completa, incluindo funções, e também
os *data types record* e *string*.

---

## Project structure

All the java source files are in the folder `src`. The folder `examples` contains
some small programs that show how the programming language implemented in this
project works.

## Build the project

Clone the repository and run the command `make package`, the file `parser.jar`
should be created.

```
$ git clone https://bitbucket.org/jbmarques/icl-tp1.git
$ cd icl-tp1
$ make package
```

#### Manual build without *make*

If you do not have *make* installed you can still build the project manually,
`cd` into the folder `src`, run `javacc` on the file `Parser.jj` and then compile
the java files with `javac *.java`. To run the program you should then use the
class file `Parser.class`.

## How to run

You can run the program in one of two modes, REPL mode or compiler mode.

To run the REPL simply do `java -jar parser.jar`, you should now be in the REPL.

To compile a file and generate the `.j` and the corresponding `.class` files you do
`java -jar parser.jar source_file.icll`.

Here is how you can run the hello world example in `examples/hello_world.icll`:

```
$ java -jar parser.jar examples/hello_world.icll
[compiler] Generated: examples/hello_world.j
[ jasmin ] Generated: examples/hello_world.class
$ java examples.hello_world
Hello world!
```

As you can see from above, the program runs `jasmin.jar` on the generated `.j`
files. If for some reason the jasmin command can not be executed a warning message
should be shown, if this happens you have to manually run jasmin on the `.j` files.
