package com.examen.denominacionesvuelto.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examen.denominacionesvuelto.dao.CurrencyDao;
import com.examen.denominacionesvuelto.entity.Currency;

@Service
public class ProcesadorVueltoServiceImpl implements ProcesadorVueltoService {

    @Autowired
    private CurrencyDao currencyDao;

    @Override
    public Integer exercise1() {
	Integer result = this.obtenerVueltoTotal(132, 5);
	System.out.println("PUNTO 1 - vuelto 132: " + result);
	return result;
    }

    @Override
    public Integer exercise2() {
	List<Integer> list = Arrays.asList(10, 25, 50, 100);
	Integer vuelto = 132;
	System.out.println("PUNTO 2 - Vuelto 132: " + listReadDenominacion(vuelto, list));
	vuelto = 124;
	System.out.println("PUNTO 2 - Vuelto 124: " + listReadDenominacion(vuelto, list));

	return null;
    }

    @Override
    public Integer exercise3() {
	String fileExercise3 = "Denominaciones_3.txt";
	System.out.println("PUNTO 3 - Vuelto 132: " + readFileDenominacion(132, fileExercise3));
	System.out.println("PUNTO 3 - Vuelto 124: " + readFileDenominacion(124, fileExercise3));
	return null;
    }

    @Override
    public Integer exercise4() {
	String fileExercise4 = "Denominaciones_4.txt";
	System.out.println("PUNTO 4 - Vuelto 124: " + calcularPorPrioridad(124, fileExercise4));
	return null;
    }

    @Override
    public Integer exercise5() {
	Integer vuelto = 132;
	Integer minVuelto = Integer.MAX_VALUE;
	Currency selectCurrency = null;
	List<Currency> listCurrency = (List<Currency>) currencyDao.findAll();
	listCurrency.sort(
		Comparator.comparingInt(Currency::getCant).thenComparing(Comparator.comparingInt(Currency::getValue)));
	listCurrency.remove(0);
	for (Currency currency : listCurrency) {
	    Integer vueltoAprox = obtenerVueltoTotal(vuelto, currency.getValue());
	    Integer cantidadUsada = vueltoAprox / currency.getValue();
	    if (minVuelto > vueltoAprox && cantidadUsada <= currency.getCant()) {
		minVuelto = vueltoAprox;
		selectCurrency = currency;
	    }
	}
	if (selectCurrency != null) {
	    Integer cantidadUsada = minVuelto / selectCurrency.getValue();
	    selectCurrency.setCant(selectCurrency.getCant() - cantidadUsada);
	    currencyDao.save(selectCurrency);
	    System.out.println("PUNTO 5 - Vuelto 132: " + minVuelto);
	    return minVuelto;
	}
	System.out.println("PUNTO 5 - Vuelto 132: No hay billetes disponible");
	return null;
    }

    private Integer obtenerVueltoTotal(int vuelto, int unDenominacion) {
	Integer cociente = Math.floorDiv(vuelto, unDenominacion);
	Integer resto = Math.floorMod(vuelto, unDenominacion);
	cociente = resto > 0 ? cociente + 1 : cociente;
	return unDenominacion * cociente;
    }

    private Integer listReadDenominacion(int vuelto, List<Integer> unosDenominaciones) {
	Integer minVuelto = Integer.MAX_VALUE;
	for (Integer denominacion : unosDenominaciones) {
	    Integer vueltoAprox = obtenerVueltoTotal(vuelto, denominacion);
	    if (minVuelto > vueltoAprox) {
		minVuelto = vueltoAprox;
	    }
	}
	return minVuelto;
    }

    private Integer readFileDenominacion(int vuelto, String fileName) {
	List<Integer> lines = listDataFile(fileName).stream().map(Integer::parseInt).collect(Collectors.toList());
	return listReadDenominacion(vuelto, lines);
    }

    private List<String> listDataFile(String fileName) {
	try {
	    ClassLoader classLoader = getClass().getClassLoader();

	    File file = new File(classLoader.getResource(fileName).getFile());
	    return Files.lines(Paths.get(file.getPath())).collect(Collectors.toList());
	} catch (IOException e) {
	    return new ArrayList<>();
	}
    }

    private Integer calcularPorPrioridad(int vuelto, String fileName) {
	List<String> listDataFile = listDataFile(fileName);
	Map<Integer, Integer> mapDenominacion = mapDenominacion(listDataFile);
	return listReadDenominacion(vuelto, mapDenominacion);
    }

    private Map<Integer, Integer> mapDenominacion(List<String> list) {
	final Map<String, Integer> priority = new LinkedHashMap<>();
	priority.put("BAJO", 0);
	priority.put("NORMAL", 1);

	return list.stream().map(s -> s.split("\\|")).filter(a -> !a[1].equals("BAJO")).collect(Collectors
		.toMap(a -> Integer.parseInt(a[0]), a -> priority.containsKey(a[1]) ? priority.get(a[1]) : -1));

    }

    private Integer listReadDenominacion(int vuelto, Map<Integer, Integer> unosDenominaciones) {
	Integer minVuelto = Integer.MAX_VALUE;
	for (Entry<Integer, Integer> denominacion : unosDenominaciones.entrySet()) {
	    Integer vueltoAprox = obtenerVueltoTotal(vuelto, denominacion.getKey());
	    if (minVuelto > vueltoAprox) {
		minVuelto = vueltoAprox;
	    }
	}
	return minVuelto;
    }

}
