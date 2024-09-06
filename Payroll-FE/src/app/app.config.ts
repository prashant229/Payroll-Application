import {
  APP_INITIALIZER,
  ApplicationConfig,
  provideZoneChangeDetection,
} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
// import {
//   AuthConfig,
//   OAuthService,
//   provideOAuthClient,
// } from 'angular-oauth2-oidc';
import { provideHttpClient } from '@angular/common/http';
import { KeycloakService } from 'keycloak-angular';

// export const authCodeFlowConfig: AuthConfig = {
//   issuer: 'http://localhost:7080/realms/Payroll',
//   tokenEndpoint: 'http://localhost:7080/realms/Payroll/protocol/openid-connect/token',
//   redirectUri: window.location.origin,
//   clientId: 'salary-client-auth',
//   responseType: 'code',
//   scope: 'openid email profile',
//   showDebugInformation: true,
// };

function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:7080',
        realm: 'Payroll',
        clientId: 'salary-client-auth',
      },
      initOptions: {
        redirectUri: 'http://localhost:4200/*',
        checkLoginIframe: false,
        // postLogoutRedirectUri: 'http://localhost:4200/logout',
      }
    });
}

// function initializeOAuth(oauthService: OAuthService): Promise<void> {
//   return new Promise((resolve) => {
//     oauthService.configure(authCodeFlowConfig);
//     oauthService.setupAutomaticSilentRefresh();
//     oauthService.loadDiscoveryDocumentAndLogin()
//       .then(() => resolve());
//   });
// }

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(),
    // provideOAuthClient(),
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService],
    },
    KeycloakService,
  ],
};
