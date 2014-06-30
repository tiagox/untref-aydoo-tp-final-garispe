package untref.aydoo.procesador;

public class Resultado {

	private int ID_bicicletaMasUsada;
	private int ID_bicicletaMenosUsada;
	private String recorridoMasRealizado;
	private double tiempoPromedioUso;
	private int ID_bicicletaUsadaMasTiempo;
	private int tiempoTotalDeUsoDeLaBicicletaMasUsada;

	public Resultado() {
	}

	public Resultado(int ID_bicicletaMasUsada, int ID_bicicletaMenosUsada,
			String recorridoMasRealizada, double tiempoPromedioUso,
			int ID_bicicletaUsadaMasTiempo, int tiempoTotalDeUsoDeLaBicicletaMasUsada) {

		this.ID_bicicletaMasUsada = ID_bicicletaMasUsada;
		this.ID_bicicletaMenosUsada = ID_bicicletaMenosUsada;
		this.recorridoMasRealizado = recorridoMasRealizada;
		this.tiempoPromedioUso = tiempoPromedioUso;
		this.ID_bicicletaUsadaMasTiempo = ID_bicicletaUsadaMasTiempo;
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
		return ID_bicicletaUsadaMasTiempo;
	}

	public void setID_bicicletaUsadaMasTiempo(int iD_bicicletaUsadaMasTiempo) {
		ID_bicicletaUsadaMasTiempo = iD_bicicletaUsadaMasTiempo;
	}

	public int getID_bicicletaMasUsada() {
		return ID_bicicletaMasUsada;
	}

	public void setID_bicicletaMasUsada(int iD_bicicletaMasUsada) {
		ID_bicicletaMasUsada = iD_bicicletaMasUsada;
	}

	public int getID_bicicletaMenosUsada() {
		return ID_bicicletaMenosUsada;
	}

	public void setID_bicicletaMenosUsada(int iD_bicicletaMenosUsada) {
		ID_bicicletaMenosUsada = iD_bicicletaMenosUsada;
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
