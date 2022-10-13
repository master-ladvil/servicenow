import Controller from '@ember/controller';
import { tracked } from '@glimmer/tracking';
import { action } from '@ember/object';

export default class AppServiceAddController extends Controller {
  @action
  submituser() {
    var name = document.getElementById('name').value;
    var phone = document.getElementById('phone').value;
    var email = document.getElementById('email').value;
    var timezone = document.getElementById('timezone').value;
    var employeenumber = document.getElementById('employeenumber').value;
    var language = document.getElementById('language').value;
    var title = document.getElementById('title').value;
    var roles = document.getElementById('roles').value;
    var street = document.getElementById('street').value;
    var username = document.getElementById('username').value;
    var city = document.getElementById('city').value;
    var gender = document.getElementById('gender').value;
    var country = document.getElementById('country').value;
    var homephone = document.getElementById('homephone').value;

    $.ajax({
      url: 'http://localhost:8080/lorduoauth/ServiceNow',
      data: {
        name: name,
        phone: phone,
        email: email,
        timezone: timezone,
        employeenumber: employeenumber,
        language: language,
        title: title,
        roles: roles,
        street: street,
        username: username,
        city: city,
        gender: gender,
        country: country,
        homephone: homephone,
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
  @action
  get() {
    var name = document.getElementById('name').value;
    var desc = document.getElementById('description').value;
    console.log(name);

    $.ajax({
      url: 'http://localhost:8080/lorduoauth/ServiceRole',
      data: {
        name: name,
        desc: desc,
      },
      method: 'POST',
      success: function (response) {
        alert(response);
      },
      error: function (xhr, status, error) {
        var errorMessage = xhr.status + ': ' + xhr.statusText;
        alert('error' + errorMessage);
      },
    });
  }
}
