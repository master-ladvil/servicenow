import Controller from '@ember/controller';
import { tracked } from '@glimmer/tracking';
import { on } from '@ember/modifier';
import { get } from '@ember/helper';
import { action } from '@ember/object';

var sf = [
  'name',
  'homephone',
  'email',
  'username',
  'title',
  'employeenumber',
  'street',
  'city',
  'country',
  'timezone',
  'gender',
  'language',
  'phone',
  'roles',
];
var db = [
  'name',
  'email',
  'title',
  'street',
  'city',
  'country',
  'timezone',
  'gender',
  'language',
  'phone',
];

export default class ServiceAddMapController extends Controller {
  @action
  get() {
    let div = document.getElementById('map');
    let tr = document.createElement('tr');
    let td = document.createElement('td');
    let td2 = document.createElement('td');

    var select2 = document.createElement('select');
    select2.setAttribute('class', 'dbselect');
    tr.appendChild(td2);
    td2.appendChild(select2);

    for (var i = 0; i < db.length; i++) {
      var option = document.createElement('option');
      option.value = db[i];
      option.text = db[i];
      select2.appendChild(option);
    }

    var select = document.createElement('select');
    select.setAttribute('class', 'sfselect');
    div.appendChild(tr);
    tr.appendChild(td);
    td.appendChild(select);

    for (var i = 0; i < sf.length; i++) {
      var option = document.createElement('option');
      option.value = sf[i];
      option.text = sf[i];
      select.appendChild(option);
    }
  }

  @action submit() {
    var sfattr = document.querySelectorAll('.sfselect');
    var dbattr = document.querySelectorAll('.dbselect');
    var sf = [];
    var db = [];
    console.log(sfattr.length);
    let datas;
    for (var i = 0; i < sfattr.length; i++) {
      sf[i] = sfattr[i].value;
      db[i] = dbattr[i].value;
    }
    let m1 = sf.toString();
    let m2 = db.toString();
    $.ajax({
      url: 'http://localhost:8080/lorduoauth/ServiceMap',
      method: 'POST',
      contentType: 'application/x-www-form-urlencoded',
      data: {
        m1: m1,
        m2: m2,
      },

      success: function (response) {
        console.log(datas);
        alert(response);
      },
      error: function (xhr, status, error) {
        var errorMessage = xhr.status + ': ' + xhr.statusText;
        alert('error' + errorMessage);
      },
    });
  }
  @action
  flush() {
    var dis = this;
    $.ajax({
      url: 'http://localhost:8080/lorduoauth/ServiceFlush',
      method: 'GET',
      success: function (response) {
        console.log('flushed');
        dis.transitionToRoute('add');
      },
      error: () => {
        console.log('flushed');
        window.location.reload();
      },
    });
  }
}
