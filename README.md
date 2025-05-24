# nf-cachebrowser

A Nextflow plugin that provides a web-based interface for visualizing and exploring Nextflow pipeline executions directly in your browser.

[![Nextflow](https://img.shields.io/badge/nextflow-%E2%89%A522.10.0-brightgreen.svg)](https://www.nextflow.io/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Version](https://img.shields.io/badge/version-0.0.1-orange.svg)](https://github.com/your-org/nf-cachebrowser/releases)

## Overview

nf-cachebrowser is a powerful Nextflow plugin that allows you to visualize pipeline executions through an intuitive web interface. Monitor your workflows, explore execution details, and analyze pipeline performance without leaving your browser.

## Features

- 🌐 **Web-based Interface** - Access pipeline execution data through a modern web UI
- 📊 **Real-time Monitoring** - Track running pipelines in real-time (only in not blocking supported storages)
- 🔍 **Detailed Execution View** - Explore individual process executions and their outputs
- 📈 **Performance Analytics** - Analyze execution times, resource usage, and bottlenecks
- 🗂️ **Cache Management** - Browse and manage Nextflow work directories and cached results
- 🔄 **Resume Capabilities** - Visualize pipeline resume points and execution flow
- 📱 **Responsive Design** - Works seamlessly on desktop and mobile devices
-
## Quick Start

### Prerequisites

- Nextflow 22.10.0 or later
- Java 8 or later
- Modern web browser

### Installation

Install globally:

```bash
nextflow plugin install nf-cachebrowser
```

### Basic Usage

Start the cache browser server:

```bash
nextflow plugin nf-cachebrowser:run
```

This will launch a web server (default: http://localhost:9999) where you can view your pipeline executions.

Run your pipeline:

```bash
nextflow run your-pipeline.nf 
```

## Commands

### Start Server

Launch the web interface server:

```bash
nextflow plugin nf-cachebrowser:run [OPTIONS]
```

Options:
- `--port, -p` - Server port (default: 9999)
- `--directory, -d` - runtime Nextflow directory 

### Stop Server

Stop the running server:

```bash
TODO!!! kill the process by the moment
```

## Web Interface

### Dashboard

The main dashboard provides:
- Overview of recent pipeline executions
- Quick access to running pipelines
- System resource usage charts (TODO!!)
- Execution statistics (TODO!!)

### Execution Details

For each pipeline execution, view:
- Execution timeline and progress (TODO!!)
- Process-level execution details
- Resource consumption graphs (TODO!!)
- Log files and error messages (TODO!!)
- Work directory contents

### Tasks Details


### Task Details


## API Endpoints

The plugin also provides a REST API for programmatic access:

- `GET /api/executions` - List all executions
- `GET /api/tasks/{id}` - Get execution details


## Troubleshooting

### Common Issues

**Server won't start:**
- Check if port is already in use
- Verify Java version compatibility
- Check file permissions in work directory

**Browser shows empty data:**
- Ensure directory specified contains a `.nextflow` folder
- Check if execution data exists in work directory
- Verify network connectivity to server


## Development

### Building from Source

```bash
git clone https://github.com/incsteps/nf-cachebrowser.git
cd nf-cachebrowser
./gradlew build
```

### Running Tests

```bash
./gradlew test
```

### Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Commit your changes: `git commit -m 'Add amazing feature'`
4. Push to the branch: `git push origin feature/amazing-feature`
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

- 📖 [Documentation](https://github.com/your-org/nf-cachebrowser/wiki)
- 🐛 [Issue Tracker](https://github.com/your-org/nf-cachebrowser/issues)
- 💬 [Discussions](https://github.com/your-org/nf-cachebrowser/discussions)
- 📧 [Email Support](mailto:support@your-org.com)

## Acknowledgments

- [Nextflow](https://www.nextflow.io/) team for the amazing workflow engine
- Contributors and community members
- Beta testers and early adopters

## Changelog

### v0.0.1-rc (2025-05-24)
- Initial release
- Web-based execution visualization
- Real-time monitoring capabilities
- RESTful API endpoints


---

**Made with ❤️ by jagedn and Incremental Steps team**