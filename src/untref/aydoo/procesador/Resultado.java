package untref.aydoo.procesador;

public class Resultado {

	private int idBicicletaMasUsada;
	private int idBicicletaMenosUsada;
	private String recorridoMasRealizado;
	private double tiempoPromedioUso;
	private int idBicicletaUsadaMasTiempo;
	private int tiempoTotalDeUsoDeLaBicicletaMasUsada;

	public Resultado() {
	}

	public Resultado(int idBicicletaMasUsada, int idBicicletaMenosUsada,
			String recorridoMasRealizado, double tiempoPromedioUso,
			int idBicicletaUsadaMasTiempo,
			int tiempoTotalDeUsoDeLaBicicletaMasUsada) {
		this.idBicicletaMasUsada = idBicicletaMasUsada;
		this.idBicicletaMenosUsada = idBicicletaMenosUsada;
		this.recorridoMasRealizado = recorridoMasRealizado;
		this.tiempoPromedioUso = tiempoPromedioUso;
		this.idBicicletaUsadaMasTiempo = idBicicletaUsadaMasTiempo;
		this.tiempoTotalDeUsoDeLaBicicletaMasUsada = tiempoTotalDeUsoDeLaBicicletaMasUsada;

	}

	public int getTiempoTotalDeUsoDeLaBicicletaMasUsada() {
		return tiempoTotalDeUsoDeLaBicicletaMasUsada;
	}

	public void setTiempoTotalDeUsoDeLaBicicletaMasUsada(
			int tiempoTotalDeUsoDeLaBicicletaMasUsada) {
		this.tiempoTotalDeUsoDeLaBicicletaMasUsada = tiempoTotalDeUsoDeLaBicicletaMasUsada;
	}

	public int getID_bicicletaUsadaMasTiempo() {
		return idBicicletaUsadaMasTiempo;
	}

	public void setID_bicicletaUsadaMasTiempo(int iD_bicicletaUsadaMasTiempo) {
		idBicicletaUsadaMasTiempo = iD_bicicletaUsadaMasTiempo;
	}

	public int getID_bicicletaMasUsada() {
		return idBicicletaMasUsada;
	}

	public void setID_bicicletaMasUsada(int iD_bicicletaMasUsada) {
		idBicicletaMasUsada = iD_bicicletaMasUsada;
	}

	public int getID_bicicletaMenosUsada() {
		return idBicicletaMenosUsada;
	}

	public void setID_bicicletaMenosUsada(int iD_bicicletaMenosUsada) {
		idBicicletaMenosUsada = iD_bicicletaMenosUsada;
	}

	public String getRecorridoMasRealizado() {
		return recorridoMasRealizado;
	}

	public void setRecorridoMasRealizado(String recorridoMasRealizado) {
		this.recorridoMasRealizado = recorridoMasRealizado;
	}

	public double getTiempoPromedioUso() {
		return tiempoPromedioUso;
	}

	public void setTiempoPromedioUso(double tiempoPromedioUso) {
		this.tiempoPromedioUso = tiempoPromedioUso;
	}

}
