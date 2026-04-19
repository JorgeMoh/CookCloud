package cookcloud.modelo;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "receta")
public class Receta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_receta;
    private String titulo;
    @Column(length = 255)
    private String resumen;
    private String pasos;
    private boolean publica;

    @ManyToOne(optional=false) @JoinColumn(name="id_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingrediente> ingredientes = new ArrayList<>();

    @ManyToMany(mappedBy = "recetasGuardadas")
    private Set<Usuario> guardadaPor = new HashSet<>();

    public Receta(String titulo, String resumen,
                  String pasos, boolean publica, Usuario usuario) {
        this.titulo = titulo;
        this.resumen = resumen;
        this.pasos = pasos;
        this.publica = publica;
        this.usuario = usuario;
    }

    public Receta() {

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public Long getId_receta() {
        return id_receta;
    }

    public void setId_receta(Long id_receta) {
        this.id_receta = id_receta;
    }

    public String getPasos() {
        return pasos;
    }

    public void setPasos(String pasos) {
        this.pasos = pasos;
    }

    public boolean isPublica() {
        return publica;
    }

    public void setPublica(boolean publica) {
        this.publica = publica;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Set<Usuario> getGuardadaPor() {
        return guardadaPor;
    }

    public void setGuardadaPor(Set<Usuario> guardadaPor) {
        this.guardadaPor = guardadaPor;
    }

    public void addIngrediente(Ingrediente ingrediente) {
        ingredientes.add(ingrediente);
        ingrediente.setReceta(this);
    }

    public void removeIngrediente(Ingrediente ingrediente) {
        ingredientes.remove(ingrediente);
        ingrediente.setReceta(null);
    }

}
