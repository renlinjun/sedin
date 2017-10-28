import axios from 'axios';
import qs from 'qs';

let base = 'http://localhost';

axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

export const requestLogin = params => {
  return axios.post(`${base}/j_spring_security_check`, qs.stringify(params)).then(res => res.data);
};

export const getUserList = params => { return axios.get(`${base}/user/list`, { params: params }); };

export const getUserListPage = params => { return axios.get(`${base}/user/list`, { params: params }); };

export const removeUser = params => { return axios.get(`${base}/user/remove`, { params: params }); };

export const batchRemoveUser = params => { return axios.get(`${base}/user/batchremove`, { params: params }); };

export const editUser = params => { return axios.post(`${base}/user/edit`, params).then(res => res.data); };

export const addUser = params => { return axios.post(`${base}/user/add`, params).then(res => res.data); };
