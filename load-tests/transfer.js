import http from "k6/http";
import { check } from "k6";

const BASE_URL = __ENV.BASE_URL;

export const options = {
 vus: 50,
duration: "30s",

  thresholds: {
    http_req_failed: ['rate<0.01'],
    http_req_duration: ['p(95)<500'],
}
};

export function setup() {
  const loginPayload = JSON.stringify({
    email: __ENV.SENDER_EMAIL,
    password: __ENV.SENDER_PASSWORD,
  });

  const params = {
    headers: {
      "Content-Type": "application/json",
    },
  };

  const loginRes = http.post(
    `${BASE_URL}/api/v1/auth/login`,
    loginPayload,
    params
  );

  check(loginRes, {
    "login successful": (r) => r.status === 200,
  });

  if (loginRes.status !== 200) {
    throw new Error(
      `Login failed: ${loginRes.status} ${loginRes.body}`
    );
  }

  return {
    token: loginRes.json("token"),
  };
}

export default function (data) {

  const payload = JSON.stringify({
    receiverId: Number(__ENV.RECEIVER_ID),
    amount: 1,
  });

  const res = http.post(
    `${BASE_URL}/api/v1/transactions/transfer`,
    payload,
    {
      headers: {
        Authorization: `Bearer ${data.token}`,
        "Content-Type": "application/json",
        "Idempotency-Key": `${__VU}-${__ITER}`,
      },
    }
  );

  const ok = check(res, {
  "transfer successful": (r) => r.status === 200,
});

if (!ok) {
  console.log(`Status: ${res.status}`);
  console.log(res.body);
}
}