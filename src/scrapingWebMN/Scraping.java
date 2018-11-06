package scrapingWebMN;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraping {
	// Obtiene la lista de categorias de la web de Menini Nicola
		public ArrayList obtenerCategorias(String url) {
			ArrayList lista = new ArrayList<String>();
			try {
				// Traigo el documento a traves de http
				Document doc =Jsoup.connect(url).get();
				
				// obtengo todos los links de la pagina
				Elements links = doc.select("li.cat-item");
				for (Element link: links) {
					// obtengo el valor de los elementos "li class="cat-item"
					// y los agrego a la lista de categrias
					lista.add(link.text());								
				}			
						
			} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
			}		
			return lista;
		}
		
		public ArrayList obtenerProductosCategoria(String categoria) {
			ArrayList lista = new ArrayList<String>();
			
			// 
			categoria = categoria.toLowerCase();
			String strCat="";
			for (int i=0; i< categoria.length(); i++) {
				char car = categoria.charAt(i);
				switch(car) {
					case 'á':
						car='a';
					break;
					case 'é':
						car='e';
					break;
					case 'í':
						car='i';
					break;
					case 'ó':
						car='o';
					break;
					case 'ú':
						car='u';
					break;
					case 'ñ':
						car='n';
					break;
					case ' ':
						car='-';
					break;				
				}
				strCat= strCat + car;
			}
			System.out.println("categoria: " + categoria);
			if (categoria.equals("dormitorio"))
				strCat = "cama";
			if (categoria.equals("escritorios"))
				strCat = "mesas-y-escritorios";
			if (categoria.equals("mesas de centro"))
				strCat = "mesas";
			
			//System.out.println("categoria: " + strCat);
			
			String urlCat ="http://menini-nicola.com/product-category/" + strCat +"/";
					
			try {
				// Traigo el documento a traves de http
				Document doc =Jsoup.connect(urlCat).get();
				
				// obtengo todos los links de la pagina
				Elements links = doc.select("h3");
				for (Element link: links) {
					// obtengo el valor de los elementos "li class="cat-item"
					// y los agrego a la lista de categrias
					lista.add(link.text().toUpperCase());								
				}			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return lista;
		}	
		
		public static String corregirNombreProducto(String nombre) {
			nombre = nombre.toLowerCase();
			String strNombre="";
			// correcion de acentos
			for (int i=0; i< nombre.length(); i++) {
				char car = nombre.charAt(i);
				switch(car) {
					case 'á':
						car='a';
					break;
					case 'é':
						car='e';
					break;
					case 'í':
						car='i';
					break;
					case 'ó':
						car='o';
					break;
					case 'ú':
						car='u';
					break;
					case 'ñ':
						car='n';
					break;
					case ' ':
						car='-';
					break;				
				}
				strNombre= strNombre + car;
			}
			
			// Correccion de nombres de productos
			if (strNombre.equals("sillon-orson-1-cuerpo"))
				strNombre="sillon-1cuerpo-orson";
			if (strNombre.equals("sofa-be-3-cuerpos"))
				strNombre="sillon-be";
			if (strNombre.equals("sofa-domino-3-cuerpos"))
				strNombre="sillon-3cuerpos-domino";
			if (strNombre.equals("sofa-mucca-3-cuerpos"))
				strNombre="sofa-mucca";
			if (strNombre.equals("sofa-orson-2-cuerpos"))
				strNombre="sillon-2cuerpos-orson";
			if (strNombre.equals("sofa-ross-3-cuerpos"))
				strNombre="sofa-ross";
			if (strNombre.equals("sofa-san-jose-3-cuerpos"))
				strNombre="sofa-sanjose-4-cuerpos";
			if (strNombre.equals("sofa-sorvete-4-cuerpos"))
				strNombre="sofa-sorvete";
			if (strNombre.equals("portadocumentos-simple-lana-y-cuero"))
				strNombre="portadocumentos-simple-simple-lana-y-cuero";
			if (strNombre.equals("mesa-de-centro-domino"))
				strNombre="mesa-centro-domino";
			if (strNombre.equals("mesa-comedor-guga"))
				strNombre="mesa-guga";
			if (strNombre.equals("reloj-de-mesa-haro-–-laqueado"))
				strNombre="reloj-de-mesa-haro-laqueado";
			if (strNombre.equals("reloj-de-mesa-haro-–-roble"))
				strNombre="haro-mesa-roble";
			if (strNombre.equals("reloj-de-pared-haro-–-laqueado"))
				strNombre="reloj-de-pared-haro-laqueado";
			if (strNombre.equals("reloj-de-pared-haro-–-roble"))
				strNombre="reloj-de-pared-haro-roble";
			if (strNombre.equals("mesa-de-centro-montevi-(circular-–-chica)"))
				strNombre="mesa-de-centro-montevi-circular-chica";
			
			
			return strNombre;
		}
		
		
		public VOProductoMN obtenerProducto(String nombre) {
			VOProductoMN producto = new VOProductoMN();			
			
			String strNombre=corregirNombreProducto(nombre);
						
			System.out.println("strNombre: "+ strNombre);		
			String urlProducto = "http://menini-nicola.com/product/" + strNombre + "/";
			
			try {
				// Traigo el documento a traves de http
				Document doc =Jsoup.connect(urlProducto).get();
				
				// obtengo el nombre del producto
				Elements nombres = doc.select("h1.product_title");
				for (Element _nombre:nombres) {
					producto.setNombre(""+_nombre.text().toUpperCase());
				}
				
				
				// obtengo el precio del producto
				Elements precios = doc.select("p.price");			
				for (Element precio: precios) {
					// elimino simbolo de $ y convierto a double
					//System.out.println((precio.text()).substring(1));
					double _precio = Double.parseDouble((precio.text()).substring(1).replace(',', '.'));
					producto.setPrecio(_precio);
				}
				
				// obtengo descripcion del producto
				Elements ps = doc.select("p");
				Element primerParrafo = ps.first();
				Element ultimoParrafo = ps.last();
				Element p;
				int i=1;
				p=primerParrafo;
				String _descripcion="";
				//System.out.println("PRIMER PARRAFO: " + p.text());			
				while ((((p.text()).indexOf("Precio en ") ==-1 ) && ((p.text()).indexOf("Precios en ") ==-1 )) && (p!=ultimoParrafo)) {											
					if (i !=1) {
						//System.out.println("PARRAFO " + i + ": " + p.text());
						_descripcion=_descripcion + p.text() + "\n\n";
					}
					p=ps.get(i);				
					i++;
				}
				producto.setDescripcion(_descripcion);
				producto.setFormaPago(ps.get(i-1).text());
				String _materiales="";
				while (((p.text()).indexOf("<strong>Materiales:</strong>") ==-1) && (p!=ultimoParrafo) ) {				
					p=ps.get(i);
					if ((!p.text().equals("There are no reviews yet.")) && (!p.text().equals("Disculpa, debes iniciar sesión para escribir un comentario."))) {
						//System.out.println("materiales: " + p.text());
						_materiales= _materiales+p.text() +"\n";
					}
					i++;
				}
				producto.setMateriales(_materiales.trim());
				
				// obtengo dimensines del producto
				Elements dimensiones = doc.select("td.product_dimensions");			
				for (Element dimension: dimensiones) {
					//System.out.println("dimensiones: " + dimension.text());
					producto.setDimensiones(dimension.text());
				}			
							
				Element image = doc.select("img.attachment-shop_single.size-shop_single.wp-post-image").first();
				String url = image.absUrl("src");
				producto.setUrlImagen(url);
				System.out.println("url: " + url);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			return producto;
		}
		
}
