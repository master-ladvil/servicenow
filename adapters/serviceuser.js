import RESTAdapter from '@ember-data/adapter/rest';
import Ember from 'ember';

export default class ServiceuserAdapter extends RESTAdapter {
  host = 'http://localhost:8080/lorduoauth';
  pathForType() {
    return 'ServiceNow';
  }
}
