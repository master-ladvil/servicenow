import Controller from '@ember/controller';
import { tracked } from '@glimmer/tracking';
import { action } from '@ember/object';

export default class ServiceAddAddbController extends Controller {
  @action
  submituser() {
    var name = document.getElementById('name').value;
    var phone = document.getElementById('phone').value;
    var email = document.getElementById('email').value;
    var timezone = document.getElementById('timezone').value;
    var language = document.getElementById('language').value;
    var title = document.getElementById('title').value;
    var street = document.getElementById('street').value;
    var city = document.getElementById('city').value;
    var gender = document.getElementById('gender').value;
    var country = document.getElementById('country').value;

    $.ajax({
      url: 'http://localhost:8080/lorduoauth/ServiceUserDb',
      data: {
        name: name,
        phone: phone,
        email: email,
        timezone: timezone,
        language: language,
        title: title,
        street: street,
        city: city,
        gender: gender,
        country: country,
      },
      method: 'POST',
      success: function (response) {
        alert(response);
      },
      error: function (xhr, status, error) {
        var errorMessage = xhr.status + ': ' + xhr.statusText;
        alert('success');
      },
    });
  }
}
