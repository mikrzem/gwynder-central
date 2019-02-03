package pl.net.gwynder.central.common.errors

class DataNotFound(missing: String) : Exception("Data not found: $missing")