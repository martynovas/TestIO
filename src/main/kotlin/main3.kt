import java.io.*
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

//Сравнение производительности io и nio
class main3 {
    val fileName = "C:\\MyTemp\\2020_11_18\\2.txt"
    val lineCount = 1000000//1000000
    val printlLineOnConsole = 0

    fun writeWithIO(){
        FileOutputStream(fileName).use {
            var w= BufferedWriter(OutputStreamWriter(it))
            for (i in 1..lineCount)  {
                w.write("0123456789\n")
            }
            w.flush()
        }
    }

    fun readWithIO(){
        FileInputStream(fileName).use {
            var br: BufferedReader = BufferedReader(InputStreamReader(it))
            var i=0
            do {
                var l = br.readLine()
                if (i<printlLineOnConsole) {
                    i++
                    println(l)
                }
            } while (l != null)
        }

    }

    fun writeWithNIO(){
        var currentTime = System.currentTimeMillis()
        val bufSize=lineCount/10 //запишем все в буфер а потом весь буфер на диск
        var buf = ByteBuffer.allocate(11*bufSize)

        do {
            for (i in '0'..'9')
                buf.put(i.toByte())
            buf.put(10)
        } while(buf.position()!=buf.capacity())
        println("capacity=${buf.capacity() } position=${buf.position()}")

        println("prepare buf time = ${System.currentTimeMillis() - currentTime}")
        currentTime = System.currentTimeMillis()

        Files.newByteChannel(Paths.get(fileName), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING).use {
            for (j in 1..lineCount/bufSize){
                buf.rewind()
                it.write(buf)
            }
        }
        println("write buf time = ${System.currentTimeMillis() - currentTime}")
    }

    fun readWithNIO(){
        val charset = Charset.forName("windows-1251")
        Files.newByteChannel(Paths.get(fileName)).use {
            var buf = ByteBuffer.allocate(1100)
            var count:Int
            var i=0

            do {
                count = it.read(buf)
                buf.rewind()
                if (count!=-1 && i<printlLineOnConsole) {
                    /*for (k in 1..11)
                      System.out.print("-${buf.get()}-");
                    println("_")*/
                    System.out.print(charset.decode(buf.slice(0,count)));
                    buf.rewind()
                    i++
                }
            }while (count!=-1)
        }
    }
}

fun main(args: Array<String>) {
    var currentTime = System.currentTimeMillis()
    val m = main3()
    m.writeWithIO()
    println("writeWithIO time = ${System.currentTimeMillis() - currentTime}")
    currentTime = System.currentTimeMillis()
    m.readWithIO()
    println("readWithIO time = ${System.currentTimeMillis() - currentTime}")
    currentTime = System.currentTimeMillis()
    m.writeWithNIO()
    println("writeWithNIO time = ${System.currentTimeMillis() - currentTime}")
    currentTime = System.currentTimeMillis()
    m.readWithNIO()
    println("readWithNIO time = ${System.currentTimeMillis() - currentTime}")
    currentTime = System.currentTimeMillis()
}