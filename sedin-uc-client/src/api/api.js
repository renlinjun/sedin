import axios from 'axios';
import qs from 'qs';

let base = 'http://localhost';

axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
// 带cookie请求
//axios.defaults.withCredentials = true
// http request 拦截器
axios.interceptors.request.use(
  config => {
    if (sessionStorage.JWT) {  // 判断是否存在token，如果存在的话，则每个http header都加上token
      config.headers.Authorization = `Bearer ${sessionStorage.JWT}`;
    }
    console.log(config);
    return config;
  },
  err => {
    return Promise.reject(err);
  });



export const requestLogin = params => {
  return axios.post(`${base}/j_spring_security_check`, qs.stringify(params) ).then(res => res.data);
};

export const getUserList = params => {
  return axios.get(`${base}/user/list`, { params: params }); };

export const getUserListPage = params => {
  return axios.get(`${base}/user/list`, { params: params }); };

export const removeUser = params => { return axios.get(`${base}/user/remove`, { params: params }); };

export const batchRemoveUser = params => { return axios.get(`${base}/user/batchremove`, { params: params }); };

export const editUser = params => { return axios.post(`${base}/user/edit`, params).then(res => res.data); };

export const addUser = params => { return axios.post(`${base}/user/add`, params).then(res => res.data); };

