import java.io.*
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption


/* Чтение с консоли символов
fun main(args: Array<String>) {
        var c:Char
        var br:BufferedReader  = BufferedReader(InputStreamReader(System.`in`))
        do {
            c = br.read().toChar()
            println(c)
        } while (c != 'q')
    }
*/

/* Чтение с консоли строк
fun main(args: Array<String>) {
    var c:String
    var br:BufferedReader  = BufferedReader(InputStreamReader(System.`in`))
    do {
        c = br.readLine()
        println(c)
    } while (c != "quit")
}
*/

/* Чтение файла
fun main(args: Array<String>) {
    var f = FileInputStream("C:\\MyTemp\\2020_11_18\\1.txt")

    do {
        var c = f.read()
        if (c!=-1)
          print(c.toChar())
    } while (c != -1)

    f.close()
}
 */

/* Чтение файла c автозакрытием
fun main(args: Array<String>) {
    FileInputStream("C:\\MyTemp\\2020_11_18\\1.txt").use {
        do {
            var c = it.read()
            if (c != -1)
                print(c.toChar())
        } while (c != -1)
    }
}
 */

/*чтение файла построчно
fun main(args: Array<String>) {
    FileInputStream("C:\\MyTemp\\2020_11_18\\1.txt").use {
        var br: BufferedReader = BufferedReader(InputStreamReader(it))
        do {
            var l = br.readLine()
            if (l != null)
                println(l)
        } while (l != null)
    }
}
 */

/* IO запись гигабайта и чтение его
fun main(args: Array<String>) {
    var currentTime = System.currentTimeMillis()
    FileOutputStream("C:\\MyTemp\\2020_11_18\\2.txt").use {
      var w= BufferedWriter(OutputStreamWriter(it))
      for (i in 1..10000000)  {
          w.write("123456789\n")
      }
        w.flush()
    }
    println("Spent time = ${System.currentTimeMillis() - currentTime}")
    currentTime = System.currentTimeMillis()
    FileInputStream("C:\\MyTemp\\2020_11_18\\2.txt").use {
        var br: BufferedReader = BufferedReader(InputStreamReader(it))
        var i=0
        do {
            var l = br.readLine()
            if (i<10) {
                i++
                println(l)
            }
        } while (l != null)
    }
    println("Spent time = ${System.currentTimeMillis() - currentTime}")

}*/


/*nio чтение файла
fun main(args: Array<String>) {
  val charset = Charset.forName("windows-1251")
   Files.newByteChannel(Paths.get("C:\\MyTemp\\2020_11_18\\2.txt")).use {
     var buf = ByteBuffer.allocate(1280)
       var count:Int
     var i=0

     do {
       count = it.read(buf)
       buf.rewind()
       if (count!=-1 && i<10) {
           System.out.print(charset.decode(buf.slice(0,count)));
           buf = buf.rewind()
           i++
       }
     }while (count!=-1)
   }
}
*/

/*nio запись
fun main(args: Array<String>) {
   val charset = Charset.forName("windows-1251")
   Files.newByteChannel(Paths.get("C:\\MyTemp\\2020_11_18\\2.txt"),StandardOpenOption.WRITE,StandardOpenOption.TRUNCATE_EXISTING).use {
       var buf = ByteBuffer.allocate(4)
       buf.put('h'.toByte())
       buf.put('o'.toByte())
       buf.put('m'.toByte())
       buf.put('e'.toByte())
       buf.rewind()
       it.write(buf)
   }
}*/

fun main(args: Array<String>) {
    var currentTime = System.currentTimeMillis()

    var buf = ByteBuffer.allocate(12)
    for (i in '0'..'9')
        buf.put(i.toByte())
    buf.put(13)
    buf.put(10)

    Files.newByteChannel(Paths.get("C:\\MyTemp\\2020_11_18\\2.txt"),StandardOpenOption.WRITE,StandardOpenOption.TRUNCATE_EXISTING).use {
    for (j in 1..1000000){
        buf.rewind()
        it.write(buf)
    }
    }

    println("Spent time = ${System.currentTimeMillis() - currentTime}")
    currentTime = System.currentTimeMillis()

    val charset = Charset.forName("windows-1251")
    Files.newByteChannel(Paths.get("C:\\MyTemp\\2020_11_18\\2.txt")).use {
        var buf = ByteBuffer.allocate(128)
        var count:Int
        var i=0

        do {
            var currentTime2 = System.currentTimeMillis()
            count = it.read(buf)
//            println("--Spent time = ${System.currentTimeMillis() - currentTime2}")
            buf.rewind()
            if (count!=-1 && i<2) {
                System.out.print(charset.decode(buf.slice(0,count)));
                buf.rewind()
                i++
            }
        }while (count!=-1)
    }

    println("Spent time = ${System.currentTimeMillis() - currentTime}")

}