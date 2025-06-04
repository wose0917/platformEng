// api.js
export const switchDataSource = (sourceType) => {
  return request({
    url: '/api/data-source/switch',
    method: 'post',
    params: { type: sourceType }
  })
}
