//
//  +FerbyError.swift
//  iosApp
//
//  Created by Lukasz Fabia on 02/09/2026.
//

import Foundation
import SharedLogic

extension FerbyError {

    func localized() -> String {
        switch onEnum(of: self) {
        case .apiError(let error):
            return error.localized()

        case .googleSsoError(let error):
            return error.localized()

        case .unknown:
            return String(localized: "unknown")
        }
    }
}

private extension ApiError {

    func localized() -> String {
        switch onEnum(of: self) {
        case .noData:
            return String(localized: "no_data_error")

        case .client:
            return String(localized: "client_error")

        case .notFound:
            return String(localized: "not_found")

        case .serialization:
            return String(localized: "serialization_error")

        case .serverSide:
            return String(localized: "server_side_error")

        case .unauthorized:
            return String(localized: "unauthorized")
        }
    }
}

private extension GoogleSsoError {

    func localized() -> String {
        switch onEnum(of: self) {
        case .cancelled:
            return String(localized: "cancelled")

        case .configuration:
            return String(localized: "configuration")

        case .missingToken:
            return String(localized: "missing_token")

        case .signInFailed:
            return String(localized: "sign_in_failed")
        }
    }
}
