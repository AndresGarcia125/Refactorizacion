import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class App {
    // Variables globales mal ubicadas y poco descriptivas
    public static final double TASA_IVA = 0.21; // Esto es el IVA
    public static final int DESCUENTO = 3000;
    public static final String fichero = "reporte_pedidos.txt"; // Archivo de salida

    public static void main(String[] args) {

        System.out.println("INICIANDO SISTEMA DE PEDIDOS v2.0 (Refactorizado)...");
        // --- CLIENTE 1 ---

        Cliente cliente1 = new Cliente("TechSolutions SL", "B12345678", "Calle Industria 55");
        Pedido pedido1 = new Pedido(cliente1);
        pedido1.agregarProducto(new Producto("Servidor Dell", 2500.0));
        pedido1.agregarProducto(new Producto("Windows Server", 800.0));

        procesarPedido(pedido1); // Código reutilizable
        // ...

        Cliente cliente2 = new Cliente("TechSolutions SL", "B12345678", "Calle Industria 55");
        Pedido pedido2 = new Pedido(cliente1);
        pedido2.agregarProducto(new Producto("Servidor Dell", 2500.0));
        pedido2.agregarProducto(new Producto("Windows Server", 800.0));

        procesarPedido(pedido2);

        System.out.println("INICIANDO SISTEMA DE PEDIDOS v1.0...");
        // --- CLIENTE 1: Datos dispersos ---
        String nombreCliente = "TechSolutions SL";
        String idCliente = "B12345678";
        String direccionCliente = "Calle Industria 55, Madrid";

        // --- CLIENTE 1: Lista de productos (Arrays paralelos, mala práctica) ---
        ArrayList<String> nombreProducto = new ArrayList<>(); // Nombres productos
        nombreProducto.add("Servidor Dell PowerEdge");
        nombreProducto.add("Licencia Windows Server");
        nombreProducto.add("Cableado Estructurado");

        ArrayList<Double> precioProducto = new ArrayList<>(); // Precios productos
        precioProducto.add(2500.00);
        precioProducto.add(800.00);
        precioProducto.add(250.50);
        // --- CLIENTE 1: CÁLCULOS (Mezclados con impresión) ---
        double total = 0; // total
        System.out.println("Procesando pedido para: " + nombreCliente);
        System.out.println("ID Cliente: " + idCliente);

        for (int i = 0; i < nombreProducto.size(); i++) {
            total = total + precioProducto.get(i);
            System.out.println("Item " + (i + 1) + ": " + nombreProducto.get(i) + " - " +
                    precioProducto.get(i) + " EUR");
        }

        // Lógica de descuento "hardcodeada"
        if (total > DESCUENTO) {
            System.out.println("Aplica descuento por gran volumen (5%)");
            total = total * 0.95;
        }
        double totalConIva = total + (total * TASA_IVA);
        System.out.println("Total Neto: " + total);
        System.out.println("Total con IVA (" + (TASA_IVA * 100) + "%): " + totalConIva);
        System.out.println("--------------------------------------------------");
        // --- CLIENTE 1: GUARDADO EN ARCHIVO (Responsabilidad mezclada) ---
        try {
            FileWriter myWriter = new FileWriter("pedido_" + idCliente + ".txt");
            myWriter.write("FACTURA\n");
            myWriter.write("Cliente: " + nombreCliente + "\n");
            myWriter.write("Direccion: " + direccionCliente + "\n");
            myWriter.write("Total a pagar: " + totalConIva + "\n");
            myWriter.close();
            System.out.println("Archivo guardado correctamente.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        System.out.println("\n\n"); // Espacios feos
        // ====================================================================
        // --- CLIENTE 2: COPY-PASTE DEL CÓDIGO ANTERIOR (El horror) ---
        // ====================================================================

        String nombreCliente2 = "Libreria Moderna";
        String idCliente2 = "A98765432";
        String direccionCliente2 = "Av. Diagonal 200, Barcelona";

        ArrayList<String> nombreProducto2 = new ArrayList<>();
        nombreProducto2.add("Pack Libros Escolares");
        nombreProducto2.add("Estantería Metálica");

        ArrayList<Double> precioProducto2 = new ArrayList<>();
        precioProducto2.add(1200.00);
        precioProducto2.add(300.00);
        double total2 = 0;
        System.out.println("Procesando pedido para: " + nombreCliente2);
        System.out.println("ID Cliente: " + idCliente2);

        for (int i = 0; i < nombreProducto2.size(); i++) {
            total2 = total2 + precioProducto2.get(i);
            System.out.println("Item " + (i + 1) + ": " + nombreProducto2.get(i) + " - " +
                    precioProducto2.get(i) + " EUR");
        }
        // Lógica de descuento repetida (y si cambiamos una, la otra se queda
        // desactualizada)
        if (total2 > DESCUENTO) {
            System.out.println("Aplica descuento por gran volumen (5%)");
            total2 = total2 * 0.95;
        }
        double totalConIva2 = total2 + (total2 * TASA_IVA);
        System.out.println("Total Neto: " + total2);
        System.out.println("Total con IVA (" + (TASA_IVA * 100) + "%): " + totalConIva2);
        System.out.println("--------------------------------------------------");
        try {
            FileWriter myWriter = new FileWriter("pedido_" + idCliente2 + ".txt");
            myWriter.write("FACTURA\n");
            myWriter.write("Cliente: " + nombreCliente2 + "\n");
            myWriter.write("Direccion: " + direccionCliente2 + "\n");
            myWriter.write("Total a pagar: " + totalConIva2 + "\n");
            myWriter.close();
            System.out.println("Archivo guardado correctamente.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void procesarPedido(Pedido pedido) {

        System.out.println("Procesando pedido para: " + pedido.getCliente().getNombre());
        System.out.println("ID Cliente: " + pedido.getCliente().getId());

        int i = 1;
        for (Producto producto : pedido.getProductos()) {
            System.out.println(
            String.format("Item %d: %s - %.2f EUR",
            i++, producto.getNombre(), producto.getPrecio()));
        }

        double totalNeto = pedido.calcularTotal();

        if (aplicarDescuento(totalNeto)) {
            System.out.println("Aplica descuento por gran volumen (5%)");
            totalNeto *= (1 - DESCUENTO);
        }

        double totalConIVA = totalNeto * (1 + TASA_IVA);

        System.out.println(String.format("Total Neto: %.2f EUR", totalNeto));
        System.out.println(String.format("Total con IVA (%.0f%%): %.2f EUR", TASA_IVA * 100, totalConIVA));
    }

    public static boolean aplicarDescuento(double total){
        return total > DESCUENTO;
    }

}
