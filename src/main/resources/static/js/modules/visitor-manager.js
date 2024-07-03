import { baseUrl } from "./baseUrl.js";

// let visitorId = 0;

// export function setVisitorId(id){
//     visitorId = id;
//     console.log("visitor id is set to ", visitorId);
// }
// export function getVisitorId(){
//     return visitorId;
// }

export const fetchVisitor = async function (id) {
  let fullUrl = `${baseUrl}/visitors/${id}`;
  return await fetch(fullUrl).then((response) => {
    if (response.status !== 302) {
      return response.json().then((data) => {
        console.log(data);
        throw new Error(data.message);
      });
    }
    return response.json();
  });
};