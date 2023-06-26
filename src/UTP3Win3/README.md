# Zadanie: Lambda-wyrażenia dla niefunkcyjnych interfejsów ?

Spowodować, by w następującycm dalej programie,  po naciśnięciu klawisza myszki na przycisku b
na konsoli zostało wypisane "ok". Pliku Main.java nie wolno modyfikować.

    public static void main(String[] args) {
        SwingUtilities.invokeLater( ()-> {
            JFrame f = new JFrame();
            JButton b = new JButton("Myszą ciśnij");
            b.addMouseListener ( (MousePressListener) e -> System.out.println("ok") );
            f.add(b);
            f.pack();
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setVisible(true);
            }
        );
    }