import { LoginService } from './../../../services/login/login.service';
import { Login } from './../../../modelo/login';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Persona } from '../../../modelo/persona';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
 // Variables para los mensajes en la pagina
 show: number;
 msj: string;
 // usuario que iniciara sesion
 usuario: Login = new Login();

 username = 'admin';
 password = '1234';

 inicio = false;

 constructor(private servicios: LoginService, private router: Router) {}

 ngOnInit() {
   console.log(this.servicios.getUsuario());
   // Validamos si el usuario ya inicio sesion
   if (this.servicios.getUsuario() != null) {
     // como ya inicio sesion, lo redireccionamos al inicio
     this.router.navigate(['/']);
   }
 }

 /**
  * Iniciar Sesion en la aplicacion
  */
 login(event) {
   this.usuario.username = this.username;
   this.usuario.contrasenia = this.password;
   console.log(this.usuario);
   // enviamos al servicio
   this.servicios.login(this.usuario).subscribe(rta => {
     if (rta == null) {
       this.msj = 'A ingresado datos incorrectos';
       this.show = 1;
     } else {
       // --- El usuario se encuentra registrado ---//
       this.usuario = rta;
       this.inicio = true;
       // Obtenemos la persona del usuario
       this.servicios.getUsuarioPersona(this.usuario).subscribe(rta2 => {
         if (rta2 == null) {
           // No se encontro la persona asociada al usuario
           this.msj = 'A ingresado datos incorrectos';
           this.show = 1;
         } else {
           // --- La persona se encuentra registrada ---//
           this.usuario.persona_cedula = rta2;
           // Obtenemos el rol de la persona
           this.servicios.getUsuarioPersonaRol(this.usuario.persona_cedula).subscribe(rta3 => {
             if (rta3 == null) {
               // No se encontro la persona asociada al usuario
               this.msj = 'A ingresado datos incorrectos';
               this.show = 1;
             } else {
               this.usuario.persona_cedula.rol_id = rta3;
               // Obtenemos los accesos del rol
               this.servicios.getUsuarioRolAccesos(this.usuario.persona_cedula.rol_id).subscribe(rta4 => {
                 this.usuario.persona_cedula.rol_id.accesos = rta4;
                 // una vez construido el objeto con el usuario, persona, rol, acceso
                 // procedemos a guardarlo como variable de sesion en angular 6
                 this.servicios.setUsuario(this.usuario);
                 // Redirigimos el usuario al inicio
                 window.location.reload();
               });
             }
           });
         }
       });
     }
   });
 }

 cerrarMsj() {
  this.show = 0;
}

 validarInicio(): boolean {
  return this.inicio;
}
}
