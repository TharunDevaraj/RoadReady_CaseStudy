import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './component/dashboard/dashboard.component';
import { RegisterComponent } from './component/register/register.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule} from '@angular/common/http';
import { LoginComponent } from './component/login/login.component';
import { ShowCarsComponent } from './component/show-cars/show-cars.component';
import { ReserveComponent } from './component/reserve/reserve.component';
import { PaymentComponent } from './component/payment/payment.component';
import { SuccessComponent } from './component/success/success.component';
import { HistoryComponent } from './component/history/history.component';
import { FeedbackComponent } from './component/feedback/feedback.component';
import { ChangeDateComponent } from './component/change-date/change-date.component';
import { AboutComponent } from './component/about/about.component';
import { ContactComponent } from './component/contact/contact.component';
import { ProfileComponent } from './component/profile/profile.component';
import { AddCarComponent } from './component/add-car/add-car.component';
import { UpdateCarComponent } from './component/update-car/update-car.component';
import { ViewUsersComponent } from './component/view-users/view-users.component';
import { ReportComponent } from './component/report/report.component';
import { CheckComponent } from './component/check/check.component';
import { WelcomeComponent } from './component/welcome/welcome.component';
import { ChangePasswordComponent } from './component/change-password/change-password.component';
import { MyFeedbackComponent } from './component/my-feedback/my-feedback.component';
import { ViewFeedbacksComponent } from './component/view-feedbacks/view-feedbacks.component'

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    RegisterComponent,
    LoginComponent,
    ShowCarsComponent,
    ReserveComponent,
    PaymentComponent,
    SuccessComponent,
    HistoryComponent,
    FeedbackComponent,
    ChangeDateComponent,
    AboutComponent,
    ContactComponent,
    ProfileComponent,
    AddCarComponent,
    UpdateCarComponent,
    ViewUsersComponent,
    ReportComponent,
    CheckComponent,
    WelcomeComponent,
    ChangePasswordComponent,
    MyFeedbackComponent,
    ViewFeedbacksComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
