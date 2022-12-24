import java.io.IOException;
import java.io.RandomAccessFile;

public class File{
    /**
     * Metodo principal
     * 
     * le um numero inteiro indicando quantos numeros reais serao
     * lidos, le essa quantidade de numeros reais e salva no arquivo
     * tmp.dat. Em seguida abre o arquivo tmp.dat e le os numeros de tras 
     * para frente printando na tela
     */
    public static void main(String[] args) throws IOException{
        int n = MyIO.readInt();
        RandomAccessFile raf = new RandomAccessFile("tmp.dat", "rw");

        for(int i = 0; i < n; i++){
            double tmp = MyIO.readDouble();
            raf.writeDouble(tmp);
        }

        raf.close();

        raf = new RandomAccessFile("tmp.dat", "r");

        for(int i = 0; i < n; i++){
            raf.seek((n - i - 1) * 8);
            double tmp = raf.readDouble();
            int tmp2 = (int)tmp;

            if(tmp == tmp2)
                MyIO.println(tmp2);
            else
                MyIO.println(tmp);
        }

        raf.close();
    }
}
