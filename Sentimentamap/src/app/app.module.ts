import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatIconModule } from '@angular/material/icon';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MapComponent } from './map/map.component';
import { BubblesComponent } from './bubbles/bubbles.component';
import { ScalesComponent } from './scales/scales.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { SideBarComponent } from './side-bar/side-bar.component';
import { PieChartComponent } from './pie-chart/pie-chart.component';
import { ChartsModule } from 'ng2-charts';
import { TimeSliderComponent } from './time-slider/time-slider.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule } from '@angular/common/http';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { ToppyModule } from 'toppy';
import { MatInputModule } from '@angular/material/input'
import { MatSelectModule } from '@angular/material/select'
import { MatButtonModule } from '@angular/material/button'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatTabsModule} from '@angular/material/tabs';
import { ScatterChartComponent } from './scatter-chart/scatter-chart.component';


@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
    BubblesComponent,
    ScalesComponent,
    LandingPageComponent,
    SideBarComponent,
    PieChartComponent,
    TimeSliderComponent,
    RegisterComponent,
    LoginComponent,
    ScatterChartComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ChartsModule,
    NgbModule,
    FormsModule,
    BrowserModule,
    MatIconModule,
    ReactiveFormsModule,
    HttpClientModule,
    ToppyModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    BrowserAnimationsModule,
    MatTabsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
