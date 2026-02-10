import java.util.ArrayList;

public class Pedido {
    private String nombre;
    private String precio;
    private Cliente cliente;
    private ArrayList productos;
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    

    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto){
        productos.add(producto);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public double calcularTotal(){
        double total = 0;
        for (Producto producto : productos){
            total += producto.getPrecio();
        }
        return total;
    }

}
