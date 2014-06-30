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
	Map<Integer, Integer> bicicletasPorTiempoDeUso;
	private boolean daemon;

	public ProcesadorEstadistico(String rutaDirectorio) {
		this.manejadorArchivos = new ManejadorArchivos(rutaDirectorio);
		this.recorridos = new ArrayList<RecorridoPorBicicleta>();
	}

	public void cargarRecorridos() throws IOException, ZipException {
		this.recorridos = this.manejadorArchivos.cargarRecorridos();
	}

	public void setDaemon(boolean daemon) {
		this.daemon = daemon;
	}

	public boolean esModoDaemon() {
		return this.daemon;
	}

	public int getBicicletaMasUsada() {
		int ID_bicicletaMasUsada = 0;
		int maximo = 0;
		int ID_auxiliar = 0;
		int cantidadIguales;

		for (int i = 0; i < recorridos.size(); i++) {
			ID_auxiliar = recorridos.get(i).getID_bicicleta();
			cantidadIguales = -1;

			for (int j = 0; j < recorridos.size(); j++) {
				if (recorridos.get(j).getID_bicicleta() == ID_auxiliar) {
					cantidadIguales++;
				}
			}

			if (cantidadIguales > maximo) {
				maximo = cantidadIguales;
				ID_bicicletaMasUsada = ID_auxiliar;
			}
		}

		return ID_bicicletaMasUsada;
	}

	public int getBicicletaMenosUsada() {
		int ID_bicicletaMenosUsada = 0;
		int minimo = recorridos.size();
		int ID_auxiliar = 0;
		int cantidadIguales;

		for (int i = 0; i < recorridos.size(); i++) {
			ID_auxiliar = recorridos.get(i).getID_bicicleta();
			cantidadIguales = -1;

			for (int j = 0; j < recorridos.size(); j++) {
				if (recorridos.get(j).getID_bicicleta() == ID_auxiliar) {
					cantidadIguales++;
				}
			}

			if (cantidadIguales < minimo) {
				minimo = cantidadIguales;
				ID_bicicletaMenosUsada = ID_auxiliar;
			}
		}

		return ID_bicicletaMenosUsada;
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
		RecorridoPorBicicleta recorridoMasRealizado = null;
		RecorridoPorBicicleta recorridoAuxiliar;
		int maximo = 0;
		int cantidadIguales;

		for (int i = 0; i < recorridos.size(); i++) {
			recorridoAuxiliar = recorridos.get(i);
			cantidadIguales = -1;

			for (int j = 0; j < recorridos.size(); j++) {
				if (recorridos.get(j).getID_estacionOrigen() == recorridoAuxiliar
						.getID_estacionOrigen()
						&& recorridos.get(j).getID_estacionDestino() == recorridoAuxiliar
								.getID_estacionDestino()) {
					cantidadIguales++;
				}
			}

			if (cantidadIguales > maximo) {
				maximo = cantidadIguales;
				recorridoMasRealizado = recorridoAuxiliar;
			}
		}

		return recorridoMasRealizado.getParOrigenDestino();
	}

	public Resultado getResultado() {
		int id_bicicletaMasUsada = getBicicletaMasUsada();
		int id_bicicletaMenosUsada = getBicicletaMenosUsada();
		String recorridoMasRealizado = getRecorridoMasRealizado();
		double tiempoPromedio = getTiempoPromedioUso();
		int id_bicicletaUsadaMasTiempo = getBicicletaMasTiempoUtilizada();
		int tiempoTotalDeUsoDeLaBicicletaMasUsada = getTiempoTotalDeUsoDeLaBicicletaMasUsada();

		Resultado resultado = new Resultado(id_bicicletaMasUsada,
				id_bicicletaMenosUsada, recorridoMasRealizado, tiempoPromedio,
				id_bicicletaUsadaMasTiempo, tiempoTotalDeUsoDeLaBicicletaMasUsada);

		return resultado;
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
			bicicletaId = recorrido.getID_bicicleta();
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

	public void generarYMLConResultado(Resultado resultado) throws IOException,
			ZipException {
		this.manejadorArchivos.escribirYML(resultado);
	}

	public void borrarRecorridosCargados() {
		this.recorridos.clear();
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
