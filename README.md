icl-compiler
============

A programming language compiler for a class on Interpreters and Compilers. The
compiler targets the JVM.

## Project structure

All the java source files are in the folder `src`. The folder `examples`
contains some small programs that show how the programming language implemented
in this project works.

## Build the project

Clone the repository and run the command `make package`, the file `parser.jar`
should be created.

```
$ git clone https://github.com/mrshankly/icl-compiler.git
$ cd icl-tp1
$ make package
```

## How to run

You can run the program in one of two modes, interpreter mode or compiler mode.

To run the interpreter simply do `java -jar parser.jar`, you should now be in
the REPL.

To compile a file and generate the `.j` and the corresponding `.class` files you
do `java -jar parser.jar source_file.icll`.

Here is how you can run the hello world example in `examples/hello_world.icll`:

```
$ java -jar parser.jar examples/hello_world.icll
[compiler] Generated: examples/hello_world.j
[ jasmin ] Generated: examples/hello_world.class
$ java examples.hello_world
Hello world!
```

As you can see from above, the program runs `jasmin.jar` on the generated `.j`
files. If for some reason the jasmin command can not be executed a warning
message should be shown, if this happens you have to manually run jasmin on the
`.j` files.

## Example

The example below shows a rudimentary implementation of estimating the value of
Pi using Monte Carlo.

```ocaml
let
    mod : (int, int)int = fun dividend:int, divisor:int ->
        dividend - divisor * (dividend / divisor)
    end

    seed : ref int = new 2

    random : ()int = fun ->
        seed := mod(8121 * !seed + 28411, 181); !seed
    end

    is_inside_circle : (int, int)bool = fun x:int, y:int ->
        (x * x) + (y * y) <= 32767
    end
in
    let
        i : ref int = new 0
        s : ref int = new 0
    in
        while !i < 30000 do
            let
                x : int = random()
                y : int = random()
            in
                if is_inside_circle(x, y) then
                    s := !s + 1
                else
                    !s
                end;
                i := !i + 1
            end
        end;
        println 4 * !s * 100 / 30000
    end
end;;
```
