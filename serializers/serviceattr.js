import RESTSerializer from '@ember-data/serializer/rest';
import Ember from 'ember';

export default class ServiceattrSerializer extends RESTSerializer {
  normalizeResponse(store, primaryModelClass, payload, id, requestType) {
    console.log('control at serializer->post->normalize');
    payload = { serviceattr: payload };
    console.log(payload);
    return super.normalizeResponse(
      store,
      primaryModelClass,
      payload,
      id,
      requestType
    );
  }
}
