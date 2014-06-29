package untref.aydoo.tests;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;
import net.lingala.zip4j.exception.ZipException;

import org.junit.After;
import org.junit.Test;

import untref.aydoo.procesador.ProcesadorEstadistico;
import untref.aydoo.procesador.ProcesadorMain;
import untref.aydoo.procesador.Resultado;

public class ProcesadorEstadisticoTest {

	@After
	public void tearDown() {
		File resultado = new File("data/resultado.yml");
		if (resultado.exists()) {
			resultado.delete();
		}

		File zipPrueba = new File("data/zip_prueba.csv");
		if (zipPrueba.exists()) {
			zipPrueba.delete();
		}
	}

	@Test
	public void laBicicletaMasUsadaDeberiaSerLaEsperadaSegunLosRecorridosCargados()
			throws IOException, ZipException {

		ProcesadorEstadistico procesador = new ProcesadorEstadistico("data");
		procesador.cargarRecorridos();

		int ID_esparado = 1205;

		Assert.assertEquals(ID_esparado, procesador.getBicicletaMasUsada());
	}

	@Test
	public void laBicicletaMenosUsadaDeberiaSerLaEsperadaSegunLosRecorridosCargados()
			throws IOException, ZipException {

		ProcesadorEstadistico procesador = new ProcesadorEstadistico("data");
		procesador.cargarRecorridos();

		int ID_esparado = 1524;

		Assert.assertEquals(ID_esparado, procesador.getBicicletaMenosUsada());
	}

	@Test
	public void laBicicletaUsadaMasTiempoDeberiaSerLaEsperadaSegunLosRecorridosCargados()
			throws IOException, ZipException {

		ProcesadorEstadistico procesador = new ProcesadorEstadistico("data");
		procesador.cargarRecorridos();

		int tiempoEsperado = 1205;

		Assert.assertEquals(tiempoEsperado,
				procesador.getBicicletaMasTiempoUtilizada());
	}

	@Test
	public void elRecorridoMasRealizadoDeberiaSerElEsperadoSegunLosRecorridosCargados()
			throws IOException, ZipException {

		ProcesadorEstadistico procesador = new ProcesadorEstadistico("data");
		procesador.cargarRecorridos();

		String recorridoEsperado = "20: ONCE - 7: PLAZA ROMA";

		Assert.assertEquals(recorridoEsperado,
				procesador.getRecorridoMasRealizado());
	}

	@Test
	public void elTiempoPromedioDeberiaSerElEsperadoSegunLosRecorridosCargados()
			throws IOException, ZipException {

		ProcesadorEstadistico procesador = new ProcesadorEstadistico("data");
		procesador.cargarRecorridos();

		double tiempoEsperado = 29.88888888888889;

		Assert.assertEquals(tiempoEsperado, procesador.getTiempoPromedioUso());
	}

	@Test
	public void elResultadoDeberiaSerElEsperado() throws IOException,
			ZipException {

		ProcesadorEstadistico procesador = new ProcesadorEstadistico("data");
		procesador.cargarRecorridos();

		int id_bicicletaMasUsadaEsparado = 1205;
		int id_bicicletaMenosUsadaEsperado = 1524;
		String recorridoMasRealizadoEsperado = "20: ONCE - 7: PLAZA ROMA";
		double tiempoPromedioEsperado = 29.88888888888889;
		int id_bicicletaUsadaMasTiempoEsperado = 1205;

		Resultado resultado = procesador.getResultado();

		Assert.assertEquals(id_bicicletaMasUsadaEsparado,
				resultado.getID_bicicletaMasUsada());
		Assert.assertEquals(id_bicicletaMenosUsadaEsperado,
				resultado.getID_bicicletaMenosUsada());
		Assert.assertEquals(recorridoMasRealizadoEsperado,
				resultado.getRecorridoMasRealizado());
		Assert.assertEquals(tiempoPromedioEsperado,
				resultado.getTiempoPromedioUso());
		Assert.assertEquals(id_bicicletaUsadaMasTiempoEsperado,
				resultado.getID_bicicletaUsadaMasTiempo());

	}

	@Test
	public void esDaemonDeberiaDevolverFalsoSiElArgumentoEsVacio()
			throws IOException, ZipException {

		String[] argumentos = { "data", "" };

		ProcesadorMain.main(argumentos);

		Assert.assertFalse(ProcesadorMain.getProcesadorEstadistico()
				.esModoDaemon());
	}

	@Test
	public void esDaemonDeberiaDevolverFalsoSiElArgumentoEsDistintoDeLoEspecificado()
			throws IOException, ZipException {

		String[] argumentos = { "data", "-onDemand" };

		ProcesadorMain.main(argumentos);

		Assert.assertFalse(ProcesadorMain.getProcesadorEstadistico()
				.esModoDaemon());
	}

	@Test
	public void esDaemonDeberiaDevolverVerdaderSiElArgumentoEsGuionD()
			throws IOException, ZipException {

		String[] argumentos = { "data", "-d" };

		ProcesadorMain.main(argumentos);

		Assert.assertTrue(ProcesadorMain.getProcesadorEstadistico()
				.esModoDaemon());
	}

	@Test
	public void esDaemonDeberiaDevolverVerdaderSiElArgumentoEsGuionDaemon()
			throws IOException, ZipException {

		String[] argumentos = { "data", "-daemon" };

		ProcesadorMain.main(argumentos);

		Assert.assertTrue(ProcesadorMain.getProcesadorEstadistico()
				.esModoDaemon());
	}

}
