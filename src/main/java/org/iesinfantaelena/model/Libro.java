package org.iesinfantaelena.model;

/**
 *  @descrition
 *	@author Carlos
 *  @date 27/10/2021
 *  @version 1.0
 *  @license GPLv3
 */

public class Libro {
    private int ISBN;
    private String titulo ;
    private String autor;
    private String editorial ;
    private int paginas ;
    private int copias ;

    public Libro() {

    }

    public Libro(int ISBN,String titulo, String autor, String editorial, int paginas, int copias) {
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.paginas = paginas;
        this.copias = copias;
    }



    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int iSBN) {
        ISBN = iSBN;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public int getCopias() {
        return copias;
    }

    public void setCopias(int copias) {
        this.copias = copias;
    }




}
