package untref.aydoo.procesador;

public class RecorridoPorBicicleta {

	private int idBicicleta;
	private int idEstacionOrigen;
	private int idEstacionDestino;
	private int tiempoUso;

	public int getIdBicicleta() {
		return idBicicleta;
	}

	public void setIdBicicleta(int idBicicleta) {
		this.idBicicleta = idBicicleta;
	}

	public int getIdEstacionOrigen() {
		return idEstacionOrigen;
	}

	public void setIdEstacionOrigen(int idEstacionOrigen) {
		this.idEstacionOrigen = idEstacionOrigen;
	}

	public int getIdEstacionDestino() {
		return idEstacionDestino;
	}

	public void setIdEstacionDestino(int idEstacionDestino) {
		this.idEstacionDestino = idEstacionDestino;
	}

	public int getTiempoUso() {
		return tiempoUso;
	}

	public void setTiempoUso(int tiempoUso) {
		this.tiempoUso = tiempoUso;
	}

	public String getParOrigenDestino() {
		return idEstacionOrigen + " - " + idEstacionDestino;
	}

}