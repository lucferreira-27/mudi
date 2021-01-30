package br.com.alura.mvc.mudi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.alura.mvc.mudi.dto.RequisicaoPedido;
import br.com.alura.mvc.mudi.model.Pedido;
import br.com.alura.mvc.mudi.model.User;
import br.com.alura.mvc.mudi.repository.PedidoRepository;
import br.com.alura.mvc.mudi.repository.UserRepository;

@Controller
@RequestMapping("pedido")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;
	

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("formulario")
	public String formulario(RequisicaoPedido requisicaoPedido) {
		return "pedido/formulario";
	}
	
	@PostMapping("novo")
	public String novo(@ModelAttribute("requisicaoPedido") @Valid RequisicaoPedido requisicaoPedido, BindingResult result) {
		
		
		if(result.hasErrors()) {
			System.out.println(result.getFieldError("urlProduto"));
			return "pedido/formulario";
		}
		
		String username =  SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByUsername(username);
		Pedido pedido = requisicaoPedido.toPedido();
		pedido.setUser(user);
		pedidoRepository.save(pedido);

		return "redirect:/home";
	}
}
