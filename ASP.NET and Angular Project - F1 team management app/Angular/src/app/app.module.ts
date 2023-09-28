import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HomeComponent } from './Home/home.component';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { SharedModule } from './modules/shared/shared.module';
import { HttpReqInterceptor } from './http-request.interceptors';
import { RouterModule } from '@angular/router';
import {HomeModule} from './Home/home.module';
import {JwtModule} from '@auth0/angular-jwt';
import {AuthGuard} from './auth-guards/auth-guards.service';
import {LoginComponent} from './modules/login/login.component';
import {FormsModule} from '@angular/forms';

export function tokenGetter() {
  return localStorage.getItem('jwt');
}

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    // importat homemodule
    HomeModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    SharedModule,

    JwtModule.forRoot({
      config: {
        tokenGetter,
        whitelistedDomains: ['localhost:44303'],
        blacklistedRoutes: []
      }
    }),
    FormsModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: HttpReqInterceptor, multi: true },
    AuthGuard
  ],

  bootstrap: [AppComponent]
})
export class AppModule {
}

