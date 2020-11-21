import java.io.File
import java.io.FileInputStream
import java.io.FileReader

fun main(args: Array<String>) {
    println("Hello World!")

    var f:File = File("C:\\MyTemp\\2020_11_18\\1.txt")
    println("File Name:${f.getName()}")
    println("Path Name:${f.getPath()}")
    println("AbsolutePath Name:${f.getAbsolutePath()}")
    println("exists ${f.exists()}")
    println("directory ${f.isDirectory()}")

    if (f.isDirectory)
        for (it in f.list())
            println(it)

    var s = FileInputStream(f)

    var i:Int = s.read()
    while (i != -1) {
        println(i)
        i = s.read()
    }

    var buf:CharArray=CharArray(50)
    var r = FileReader(f)
    r.read(buf)
    println(buf)
    r.close();
}