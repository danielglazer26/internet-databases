import {Component, OnInit} from '@angular/core';
import {environment} from "../environments/environment";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent  implements OnInit{
  title = 'Frontend';

  ngOnInit(): void {
    let cc = window as any;
    cc.cookieconsent.initialise({
      palette: {
        popup: {
          background: "#0f1011"
        },
        button: {
          background: "#00b6bd",
          text: "#0f1011"
        }
      },
      theme: "classic",
      content: {
        message: "Aby świadczyć usługi na najwyższym poziomie i w trosce o bezpieczeństwo użytkowników używamy plików" +
          " cookies niezbędnych do funkcjonowania" +
          " strony.",
        dismiss: "OK",
        link: "Więcej informacji",
        href: "http://localhost:8080/public/getPrivacyPolicy"
      }
    });
  }

/* " Za Twoją zgodą możemy także użyć plików cookies, które nie są niezbędne, aby poprawić doświadczenie użytkownika i do analizy ruchu na stronie. Klikając „Zaakceptuj”, wyrażają Państwo zgodę na używanie cookies przez naszą stronę, tak jak opisano w „Polityce ciasteczek”. Mogą zmienić Państwo swoje ustawienia cookies w dowolnym momencie klikając „Preferencje ciasteczek”."*/
}
