package types

type ApiExchangeRateResponse struct {
	Result             string  `json:"string"`
	Documentation      string  `json:"documentation"`
	TimeLastUpdateUnix int64   `json:"time_last_update_unix"`
	TimeLastUpdateUtc  string  `json:"time_last_update_utc"`
	ConversionRate     float64 `json:"conversion_rate"`
}
