import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'

const ExpiresInKey = 'Admin-Expires-In'

const UserName = 'userName';

const UserId = 'UserId';

const UserComId = 'userComId';

//获取 设置 移除 token
export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

//获取 设置 移除 过期时间
export function getExpiresIn() {
  return Cookies.get(ExpiresInKey) || -1
}

export function setExpiresIn(time) {
  return Cookies.set(ExpiresInKey, time)
}

export function removeExpiresIn() {
  return Cookies.remove(ExpiresInKey)
}

//获取 设置 移除 用户名
export function getUserName() {
  return Cookies.get(UserName) || '';
}

export function setUserName(userName) {
  return Cookies.set(UserName, userName);
}

export function removeUserName() {
  return Cookies.remove(UserName);
}

//获取 设置 移除 用户Id
export function getUserId() {
  return Cookies.get(UserId) || '';
}

export function setUserId(userId) {
  return Cookies.set(UserId, userId);
}

export function removeUserId() {
  return Cookies.remove(UserId);
}

//获取 设置 移除 用户ComID
export function getUserComId() {
  return Cookies.get(UserComId) || '';
}

export function setUserComId(userComId) {
  return Cookies.set(UserName, userComId);
}

export function removeUserComId() {
  return Cookies.remove(UserComId);
}
