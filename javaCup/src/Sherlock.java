
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Sherlock {

    private static String path0;
    private static String path;
    private static String packagePath = "org.javahispano.javacup.tacticas.tacticas_aceptadas";

    static class Registro implements Comparable {

        String origen;
        String destino;
        int porcentaje;
        int nivel;
        Class clase;

        public Registro(String origen, String destino, int porcentaje) {
            this.origen = origen;
            this.destino = destino;
            this.porcentaje = porcentaje;
            File f = new File(path0 + destino);
            try {
                String s = origen.substring(9).replaceAll("\\\\", ".");
                if (s.endsWith(".java")) {
                    s = s.substring(0, s.length() - 5).trim();
                }
                clase = Class.forName(s);
            } catch (Exception e) {
                System.out.println(f + " " + e.getMessage());
            }
            this.nivel = porcentaje * (int) f.length() / 100;
        }

        @Override
        public int compareTo(Object o) {
            if (o instanceof Registro) {
                return nivel - ((Registro) o).nivel;
            }
            return 0;
        }

        @Override
        public String toString() {
            return "[" + nivel + "]" + porcentaje + "% " + destino.substring(packagePath.length() + 10) + " -> " + origen.substring(packagePath.length() + 10);
        }
    }

    private static LinkedList<File> getFiles(String path) {
        LinkedList<File> l = new LinkedList<File>();
        File[] hijos = new File(path).listFiles();
        if (hijos == null) {
            System.out.println(path);
        } else {
            for (File f : hijos) {
                if (f.isDirectory()) {
                    l.addAll(getFiles(f.getAbsolutePath()));
                } else {
                    if (f.getAbsolutePath().endsWith(".java") && !f.getAbsolutePath().endsWith("Detalle.java")) {
                        l.add(f);
                    }
                }
            }
        }
        return l;
    }
    private static String[] words = new String[]{"File", "URL", "Runtime", "java.io."};

    public static void checkSecurity(File f) throws FileNotFoundException, IOException {
        byte[] buffer = new byte[4096];
        int read = 0;
        StringBuilder sb = new StringBuilder();
        FileInputStream fis = new FileInputStream(f);
        while ((read = fis.read(buffer)) > -1) {
            sb.append(new String(buffer, 0, read));
        }
        fis.close();
        String st = sb.toString();
        for (String s : words) {
            if (st.contains(s)) {
                System.out.println(f.toString().substring(path.length()) + " contains " + s);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Sherlock");
        JTextArea text = new JTextArea();
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(text);
        frame.add(scroll);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        path0 = new File(".").getAbsolutePath();
        path0 = path0.substring(0, path0.length() - 1);
        path = path0 + "tacticas\\org\\javahispano\\javacup\\tacticas\\tacticas_aceptadas";
        LinkedList<File> files = getFiles(path);
        StringBuffer command = new StringBuffer();
        command.append("sherlock -o resultados");
        for (File f : files) {
            checkSecurity(f);
            command.append(" \"").append(f.getAbsolutePath().substring(path.length() - 60)).append("\"");
        }
        text.append(command + "\n\n");
        Runtime.getRuntime().exec(command.toString());
        File f = new File("resultados");
        int size = (int) f.length();
        FileInputStream fis = new FileInputStream(f);
        byte[] b = new byte[size];
        fis.read(b);
        String result = new String(b, 0, size);
        int idx;
        while ((idx = result.indexOf(path)) > -1) {
            result = result.substring(0, idx) + result.substring(idx + path.length());
        }
        if (result.trim().length() > 0) {
            String resuls[] = result.split("\n");
            ArrayList<Registro> lista = new ArrayList<Registro>();
            Registro reg = null;

            for (String s : resuls) {
                reg = new Registro(s.substring(s.indexOf(" and ") + 5, s.lastIndexOf(' ') - 1),
                        s.substring(0, s.indexOf(' ')),
                        Integer.parseInt(s.substring(s.lastIndexOf(' ') + 1, s.length() - 2)));
                if (!reg.origen.equals("\\Sherlock.java") && !reg.destino.equals("\\Sherlock.java")) {
                    lista.add(reg);
                }
            }
            Collections.sort(lista);
            for (Registro r : lista) {
                    text.append(r.toString());
                    text.append("\n");
            }
        }
    }
}
