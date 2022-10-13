import Controller from '@ember/controller';
import { tracked } from '@glimmer/tracking';
import { action } from '@ember/object';

export default class AppSalesController extends Controller {
  @action
  sync() {
    var dis = this;
    $.ajax({
      url: 'http://localhost:8080/lorduoauth/ServletSyncer?id=salesforce',
      method: 'GET',
      success: function (response) {
        alert('synced');
      },
      error: () => {
        console.log('flushed');
      },
    });
  }
}
