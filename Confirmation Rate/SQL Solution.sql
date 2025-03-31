# Write your MySQL query statement below
SELECT
    Signups.user_id,
    ROUND(CASE
        WHEN COUNT(Confirmations.action) = 0 THEN 0
        ELSE SUM(CASE WHEN Confirmations.action = 'confirmed' THEN 1 ELSE 0 END)
             / COUNT(Confirmations.action)
    END, 2) AS confirmation_rate
FROM
    Signups
LEFT JOIN
    Confirmations
ON
    Signups.user_id = Confirmations.user_id
GROUP BY
    Signups.user_id