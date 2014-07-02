package untref.aydoo.procesador;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.lingala.zip4j.exception.ZipException;

public class ProcesadorEstadistico {

	private ManejadorArchivos manejadorArchivos;
	private List<RecorridoPorBicicleta> recorridos;
	private Map<Integer, Integer> bicicletasPorTiempoDeUso;
	private boolean daemon;

	public ProcesadorEstadistico(String rutaDirectorio) {
		this.manejadorArchivos = new ManejadorArchivos(rutaDirectorio);
		this.recorridos = new ArrayList<RecorridoPorBicicleta>();
	}

	public void cargarRecorridos() throws IOException, ZipException {
		this.recorridos = this.manejadorArchivos.cargarRecorridos();
	}

	public int getBicicletaMasUsada() {
		Map<Integer, Integer> acumuladordeBicicletas = new HashMap<Integer, Integer>();
		Integer idBicicleta;
		Integer cantidad;
		Integer maximo = 0;
		Integer idBicicletaMasUsada = null;

		for (int i = 0; i < recorridos.size(); i++) {
			idBicicleta = recorridos.get(i).getIdBicicleta();
			cantidad = acumuladordeBicicletas.get(idBicicleta);
			cantidad = (cantidad != null) ? cantidad + 1 : 1;

			acumuladordeBicicletas.put(idBicicleta, cantidad);

			if (cantidad > maximo) {
				maximo = cantidad;
				idBicicletaMasUsada = idBicicleta;
			}
		}

		return idBicicletaMasUsada;
	}

	public int getBicicletaMenosUsada() {
		Map<Integer, Integer> acumuladordeBicicletas = new HashMap<Integer, Integer>();
		Integer idBicicleta;
		Integer cantidad;
		Integer minimo = recorridos.size();
		Integer idBicicletaMenosUsada = null;

		for (int i = 0; i < recorridos.size(); i++) {
			idBicicleta = recorridos.get(i).getIdBicicleta();
			cantidad = acumuladordeBicicletas.get(idBicicleta);
			cantidad = (cantidad != null) ? cantidad + 1 : 1;

			acumuladordeBicicletas.put(idBicicleta, cantidad);
		}

		for (Entry<Integer, Integer> entry : acumuladordeBicicletas.entrySet()) {
			cantidad = entry.getValue();
			if (cantidad < minimo) {
				minimo = cantidad;
				idBicicletaMenosUsada = entry.getKey();
			}
		}

		return idBicicletaMenosUsada;
	}

	public double getTiempoPromedioUso() {
		double tiempoTotal = .0f;
		double tiempoPromedio = .0f;

		for (int i = 0; i < recorridos.size(); i++) {
			tiempoTotal = tiempoTotal + recorridos.get(i).getTiempoUso();
		}

		tiempoPromedio = tiempoTotal / recorridos.size();

		return (tiempoPromedio);
	}

	public String getRecorridoMasRealizado() {
		Map<String, Integer> acumuladordeParesOrigenDestino = new HashMap<String, Integer>();
		String parOrigenDestino;
		Integer cantidad;
		Integer maximo = 0;
		String parOrigenDestinoMasUsado = null;

		for (int i = 0; i < recorridos.size(); i++) {
			parOrigenDestino = recorridos.get(i).getParOrigenDestino();
			cantidad = acumuladordeParesOrigenDestino.get(parOrigenDestino);
			cantidad = (cantidad != null) ? cantidad + 1 : 1;

			acumuladordeParesOrigenDestino.put(parOrigenDestino, cantidad);

			if (cantidad > maximo) {
				maximo = cantidad;
				parOrigenDestinoMasUsado = parOrigenDestino;
			}
		}

		return parOrigenDestinoMasUsado;
	}

	public int getBicicletaMasTiempoUtilizada() {
		generarTotalesDeTiemposDeUsoDeBicicletas();

		int bicicletaId = buscarBicicletaUsadaMasTiempo();

		return bicicletaId;
	}

	private void generarTotalesDeTiemposDeUsoDeBicicletas() {
		bicicletasPorTiempoDeUso = new HashMap<Integer, Integer>();

		Integer bicicletaId;
		Integer tiempoDeUso;
		Integer tiempoDeUsoAcumulado;

		for (RecorridoPorBicicleta recorrido : recorridos) {
			bicicletaId = recorrido.getIdBicicleta();
			tiempoDeUso = recorrido.getTiempoUso();

			tiempoDeUsoAcumulado = bicicletasPorTiempoDeUso.get(bicicletaId);
			tiempoDeUsoAcumulado = (tiempoDeUsoAcumulado != null) ? tiempoDeUsoAcumulado
					: 0;

			bicicletasPorTiempoDeUso.put(bicicletaId, tiempoDeUsoAcumulado
					+ tiempoDeUso);
		}
	}

	private Integer buscarBicicletaUsadaMasTiempo() {
		Integer bicicletaId = bicicletasPorTiempoDeUso.entrySet().iterator()
				.next().getKey();
		Integer maximoTiempo = bicicletasPorTiempoDeUso.get(bicicletaId);

		for (Entry<Integer, Integer> entry : bicicletasPorTiempoDeUso
				.entrySet()) {
			if (entry.getValue() > maximoTiempo) {
				bicicletaId = entry.getKey();
				maximoTiempo = entry.getValue();
			}
		}
		return bicicletaId;
	}

	public int getTiempoTotalDeUsoDeLaBicicletaMasUsada() {
		int bicicletaMasUsada = getBicicletaMasTiempoUtilizada();
		return bicicletasPorTiempoDeUso.get(bicicletaMasUsada);
	}

	public Resultado getResultado() {
		int idBicicletaMasUsada = getBicicletaMasUsada();
		int idBicicletaMenosUsada = getBicicletaMenosUsada();
		String recorridoMasRealizado = getRecorridoMasRealizado();
		double tiempoPromedio = getTiempoPromedioUso();
		int idBicicletaUsadaMasTiempo = getBicicletaMasTiempoUtilizada();
		int tiempoTotalDeUsoDeLaBicicletaMasUsada = getTiempoTotalDeUsoDeLaBicicletaMasUsada();

		Resultado resultado = new Resultado(idBicicletaMasUsada,
				idBicicletaMenosUsada, recorridoMasRealizado, tiempoPromedio,
				idBicicletaUsadaMasTiempo,
				tiempoTotalDeUsoDeLaBicicletaMasUsada);

		return resultado;
	}

	public void generarYMLConResultado(Resultado resultado) throws IOException,
			ZipException {
		this.manejadorArchivos.escribirYML(resultado);
	}

	public void borrarRecorridosCargados() {
		this.recorridos.clear();
	}

	public void setDaemon(boolean daemon) {
		this.daemon = daemon;
	}

	public boolean esModoDaemon() {
		return this.daemon;
	}

	public void modoDaemon() throws IOException, ZipException {
		this.recorridos = new ArrayList<RecorridoPorBicicleta>();
		List<File> ZIPs = this.manejadorArchivos.getListaZIPs();

		Iterator<File> itZips = ZIPs.iterator();

		while (itZips.hasNext()) {
			File archivoZip = itZips.next();
			this.recorridos.addAll(this.manejadorArchivos
					.obtenerRecorridos(archivoZip));
			Resultado resultado = this.getResultado();
			this.generarYMLConResultado(resultado);
			this.borrarRecorridosCargados();
		}
	}

	public void modoOnDemand() throws IOException, ZipException {
		this.cargarRecorridos();
		Resultado resultado = this.getResultado();
		this.generarYMLConResultado(resultado);
	}

}
