package com.examen.denominacionesvuelto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.examen.denominacionesvuelto.service.ProcesadorVueltoService;

@RestController
public class ProcesadorVueltoController {
    @Autowired
    private ProcesadorVueltoService procesadorVueltoService;
    
    @GetMapping("/vuelto/exercise1")
    public Integer exercise1() {
	return procesadorVueltoService.exercise1();
    }
    
    @GetMapping("/vuelto/exercise2")
    public Integer exercise2() {
	return procesadorVueltoService.exercise2();
    }
    
    @GetMapping("/vuelto/exercise3")
    public Integer exercise3() {
	return procesadorVueltoService.exercise3();
    }
    
    @GetMapping("/vuelto/exercise4")
    public Integer exercise4() {
	return procesadorVueltoService.exercise4();
    }
    
    @GetMapping("/vuelto/exercise5")
    public Integer exercise5() {
	return procesadorVueltoService.exercise5();
    }

}
