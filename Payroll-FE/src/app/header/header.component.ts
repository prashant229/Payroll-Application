import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
// import { OAuthService } from 'angular-oauth2-oidc';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  isAuthenticated: boolean = true;
  
  constructor(private keyCloakService: KeycloakService) {  }
  
  logout(){
    this.keyCloakService.logout('http://localhost:4200');
  }
}
