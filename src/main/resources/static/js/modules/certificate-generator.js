import { baseUrl } from "./base-url.js";

export async function showCertificate(appearanceType, templateNo, object) {
  const response = await fetch(`${baseUrl}/certificate/${templateNo}?appearanceType=${appearanceType}`, {
    method: "POST",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
    body: JSON.stringify(object),
  });

  if (!response.ok) {
    return response.json().then((data) => {
      throw new Error(data.message);
    });
  }

  const responseData = await response.json();

  window.open(`${baseUrl}/${responseData.fileLink}`, "_blank");
}