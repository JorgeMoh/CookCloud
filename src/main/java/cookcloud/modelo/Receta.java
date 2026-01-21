package cookcloud.modelo;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "receta")
public class Receta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_receta;
    private String titulo;
    private String pasos;
    private boolean publica;

    @ManyToOne(optional=false) @JoinColumn(name="id_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ingrediente> ingredientes = new HashSet<>();

    @ManyToMany(mappedBy = "recetasGuardadas")
    private Set<Usuario> guardadaPor = new HashSet<>();

    public Receta(String titulo, String pasos, boolean publica, Usuario usuario) {
        this.titulo = titulo;
        this.pasos = pasos;
        this.publica = publica;
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public Set<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(Set<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Set<Usuario> getGuardadaPor() {
        return guardadaPor;
    }

    public void setGuardadaPor(Set<Usuario> guardadaPor) {
        this.guardadaPor = guardadaPor;
    }
}
